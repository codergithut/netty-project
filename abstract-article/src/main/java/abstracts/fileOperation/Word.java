package abstracts.fileOperation;

public class Word {

	private String word; // 词名
	private int frequency; // 词频
	
	public Word(String aWord, int aFrequency) {
		word = aWord;
		frequency = aFrequency;
	}

	public String getWord() {
		return word;
	}
	public int getFrequency() {
		return frequency;
	}
	
	public void setWord(String aWord) {
		word = aWord;
	}
	public void setFrequency(int aFrequency) {
		frequency = aFrequency;
	}
}
