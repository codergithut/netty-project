package abstracts.fileOperation;

public class Sentence {

	private String sentence = ""; // 原始句子
	private String sentence1 = ""; // 分词后的句子
	private int length = 0; // 句子长度
	private int length1 = 0; // 分词后句子长度
	private double weigh = 0; // 句子总权重
	
	public Sentence(String aSentence, String aSentence1) {
		sentence = aSentence;
		sentence1 = aSentence1;
	}
	
	public String getSentence() {
		return sentence;
	}
	public String getSentence1() {
		return sentence1;
	}
	public int getLength() {
		return length;
	}
	public int getLength1() {
		return length1;
	}
	public double getWeigh() {
		return weigh;
	}
	
	public void setSentence(String aSentence) {
		sentence = aSentence;
	}
	public void setSentence1(String aSentence1) {
		sentence1 = aSentence1;
	}
	public void setLength(int aLength) {
		length = aLength;
	}
	public void setLength1(int aLength1) {
		length1 = aLength1;
	}
	public void setWeigh(double aWeigh) {
		weigh = aWeigh;
	}
}
