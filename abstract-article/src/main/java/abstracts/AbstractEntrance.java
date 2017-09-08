package abstracts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import abstracts.fileOperation.Sentence;
import abstracts.fileOperation.SentenceComparator;
import abstracts.fileOperation.Word;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;
import com.huaban.analysis.jieba.SegToken;

public class AbstractEntrance {

	private ArrayList<Word> word; // 名词动词统计
	private LinkedList<Sentence> sentence; // 句子统计
	private String current_sentence = ""; // 当前句子
	private String current_sentence1 = ""; // 当前句子分词后结果
	private String abstracts = ""; // 文摘
	
	String[] terminators = new String[] {"。", "？", "！", "……", "\t", "\n"}; // 设置常用句子结束标点符
	String[] spaces = new String[] {" ", "　", "\t"};
	
	public String getAbstractByContent(String content, int length) { // 算法主体入口，给出原文本内容和期望文摘输出长度
		
		JiebaSegmenter segmenter = new JiebaSegmenter();
		List<SegToken> tokens = segmenter.process(content, SegMode.SEARCH);
		
		word = new ArrayList<Word>();
		sentence = new LinkedList<Sentence>();
		abstracts = "";
		
		preTreatment(tokens);
		sentenceWeigh();
		getAbstract(length);
		
		return abstracts;
	}
	
	private void preTreatment(List<SegToken> tokens) { // 预处理，切分句子并获取名词动词词频
		for(SegToken token : tokens) {
						
			if(StringUtils.isBlank(token.word.getTokenType())) {				
				if(isTerminator(token.word.getToken())) {
					if(!(token.word.getToken().equals("\n") || token.word.getToken().equals("\t"))) {
						current_sentence += token.word.getToken().toString();
						current_sentence1 += token.word.getToken().toString() + " ";
					}
					if(current_sentence.length() > 0 && current_sentence1.length() < 1000) {
						sentence.add(new Sentence(current_sentence, current_sentence1));
					}
					current_sentence = "";
					current_sentence1 = "";
				}
				else {
					if(!isSpace(token.word.getToken())) {
						current_sentence += token.word.getToken().toString();
						current_sentence1 += token.word.getToken().toString() + " ";
					}
				}
			}
			else {
				current_sentence += token.word.getToken().toString();
				current_sentence1 += token.word.getToken().toString() + " ";
				if(token.word.getTokenType().substring(0, 1).equals("n") || token.word.getTokenType().substring(0, 1).equals("v")) {
					boolean addWord = true;
					for(Word wd : word) {
						if(wd.getWord().equals(token.word.getToken().toString())) {
							wd.setFrequency(wd.getFrequency()+1);
							addWord = false;
							break;
						}
					}
					if(addWord) {
						word.add(new Word(token.word.getToken(), 1));
					}
				}
			}
		}
	}
	
	private void sentenceWeigh() { // 句子权重=名词动词总词频/句子长度
		for(Sentence st : sentence) {
			st.setLength(st.getSentence().length());
			st.setLength1(st.getSentence1().length());
			
			char[] st_char = new char[1000];
			if(st.getLength1() < 1000) {
				st.getSentence1().getChars(0, st.getLength1(), st_char, 0);
			}
			else {
				st.setWeigh(0);
				continue;
			}
			
			int i=0, j=0;
			for(i=0, j=0; i < st.getLength1(); i++) {
				if(st_char[i] == ' ') {
					st.setWeigh(st.getWeigh() + addFreq(st.getSentence1().substring(j, i)));
					j = i + 1;
				}
			}
			st.setWeigh(st.getWeigh() / st.getLength());
		}
		
	}
	
	private void getAbstract(int length) { // 获取最终文摘
		Collections.sort(sentence, new SentenceComparator());
		
		int abstractLength = 0;
		for(Sentence st : sentence) {
			abstractLength += st.getLength();
			if(st.getLength() < 20 || abstractLength > length) {
				abstractLength -= st.getLength();
				continue;
			}
			abstracts += st.getSentence();
		}
	}
	
	private boolean isTerminator(String token) { // 判断是否为句子结束标点符
		for(String terminator : terminators) {
			if(terminator.equals(token)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isSpace(String token) { // 判断是否为空白符
		for(String sp : spaces) {
			if(sp.equals(token)) {
				return true;
			}
		}
		return false;		
	}
	
	private int addFreq(String token) { // 返回词频作为句子加权的数值
		for(Word wd : word) {
			if(wd.getWord().equals(token)) {
				return wd.getFrequency();
			}
		}
		return 0;
	}
	
}
