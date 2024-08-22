package com.mdubravac.fonts.utils;

import com.mdubravac.fonts.entities.SurveyText;
import com.mdubravac.fonts.entities.TextCollection;
import com.mdubravac.fonts.repositories.SurveyTextRepository;
import com.mdubravac.fonts.repositories.TextCollectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

    private final SurveyTextRepository surveyTextRepository;

    private final TextCollectionRepository textCollectionRepository;

    public void run(ApplicationArguments args) {
        Set<String> surveyTexts = Set.of("Da li ste znali da psi imaju neverovatno razvijen njuh, čak do 100.000 puta jači od ljudskog? Njihovi mirisni receptori omogućavaju im da otkriju različite mirise, poput eksploziva ili bolesti, što ih čini nezamenljivim u brojnim specijalizovanim službama. Ova sposobnost čini ih jedinstvenim partnerima za ljude u različitim situacijama.", "Da li ste znali da mačke provode oko dve trećine svog života spavajući? To znači da mačka od devet godina zapravo budna provede samo tri godine. Ova potreba za spavanjem povezana je sa njihovim lovačkim instinktom, jer im pomaže da sačuvaju energiju za lov.", "Da li ste znali da miševi mogu da se provuku kroz otvore veličine novčića? Njihova fleksibilna tela omogućavaju im da prolaze kroz veoma uske prostore, što ih čini izuzetno sposobnim da pronađu skloništa i izvore hrane. Ova osobina pomaže im da prežive u različitim okruženjima, od urbanih područja do prirodnih staništa.");
        TextCollection textCollection = new TextCollection();
        textCollection.setName("TestSurvey");
        textCollection.setFonts(List.of("Arial", "Times", "Andale Mono", "Comic Sans MS", "Impact"));
        textCollection = textCollectionRepository.save(textCollection);
        for (String text : surveyTexts) {
            SurveyText surveyText = new SurveyText();
            surveyText.setText(text);
            surveyText.setCollection(textCollection);
            surveyTextRepository.save(surveyText);
        }
    }
}
