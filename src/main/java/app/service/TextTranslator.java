package app.service;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateException;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

/**
 * This class uses Google Translate API to provide the translations into target
 * language.
 * 
 * @author AdarshSinghal
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

	/**
	 * @param text
	 * @return translatedText
	 */
	public String translate(String text) {
		return translate(text, targetLanguage);
	}

	public String translate(String text, String targetLang) {
		return translate(text, targetLang, "en");
	}

	/**
	 * @param text
	 * @param targetLanguage
	 * @param sourceLanguage
	 * @return translatedText
	 */
	public String translate(String text, String targetLanguage, String sourceLanguage) {

		if (targetLanguage.equals(sourceLanguage)) {
			return text;
		}

		TranslateOption tgtLangTranslateOption = TranslateOption.targetLanguage(targetLanguage);
		TranslateOption srcLangTranslateOption = TranslateOption.sourceLanguage(sourceLanguage);
		Translate translate = TranslateOptions.getDefaultInstance().getService();

		Translation translationService;
		try {

			translationService = translate.translate(text, srcLangTranslateOption, tgtLangTranslateOption);
		} catch (TranslateException e) {
			// If fail to translate, then don't translate.
			return text;
		}

		return translationService.getTranslatedText();
	}

}
