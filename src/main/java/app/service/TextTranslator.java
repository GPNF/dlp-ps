package app.service;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateException;
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

		TranslateOption tgtLangTranslateOption = TranslateOption.targetLanguage(targetLang);
		TranslateOption srcLangTranslateOption = TranslateOption.sourceLanguage(srcLang);
		Translate translate = TranslateOptions.getDefaultInstance().getService();
		Translation translationService;
		try {

			translationService = translate.translate(text, srcLangTranslateOption,
					tgtLangTranslateOption);
		} catch(TranslateException e) {
			return text;
		}
		
		String translatedText = translationService.getTranslatedText();
		return translatedText;
	}

}
