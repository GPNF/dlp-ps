package app.service;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

/**
 * @author adarshsinghal
 *
 */
public class TextTranslator {

	private String targetLanguage;

	public TextTranslator() {
		targetLanguage = "en";
	}

	public TextTranslator(String targetLanguage) {
		this.targetLanguage = targetLanguage;
	}

	public String translate(String text) {
		String translatedText = translate(text, targetLanguage);
		return translatedText;
	}

	public String translate(String text, String targetLang) {
		String translatedText = translate(text, targetLang, "en");
		return translatedText;
	}

	public String translate(String text, String targetLang, String srcLang) {

		if (targetLang.equals(srcLang)) {
			return text;
		}

		Translate translate = TranslateOptions.getDefaultInstance().getService();
		Translation translation = translate.translate(text, TranslateOption.sourceLanguage(srcLang),
				TranslateOption.targetLanguage(targetLang));
		String translatedText = translation.getTranslatedText();
		System.out.printf("Translation: %s%n", translatedText);
		return translatedText;
	}

}
