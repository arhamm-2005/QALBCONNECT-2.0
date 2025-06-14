// src/main/java/com/qalbconnect/qalbconnect_backend/service/VerseService.java
package com.qalbconnect.qalbconnect_backend.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qalbconnect.qalbconnect_backend.model.Verse;
import com.qalbconnect.qalbconnect_backend.repository.VerseRepository;

import jakarta.annotation.PostConstruct;

@Service
public class VerseService {

    private final VerseRepository verseRepository;

    @Autowired
    public VerseService(VerseRepository verseRepository) {
        this.verseRepository = verseRepository;
    }

    public List<Verse> getVersesByMood(String mood) {
        return verseRepository.findByMood(mood);
    }

    // This method will run automatically after the service is constructed
    // It populates the database with sample verses if the collection is empty
    @PostConstruct
    public void initDatabase() {
        if (verseRepository.countBy() == 0) { // Check if collection is empty
            System.out.println("Initializing Verses by Mood database with sample data...");
            List<Verse> sampleVerses = Arrays.asList(
                new Verse("Happiness",
                    "فَبِأَيِّ آلَاءِ رَبِّكُمَا تُكَذِّبَانِ",
                    "Then which of the favors of your Lord will you deny?",
                    "Quran 55:13", Collections.singletonList("joy")),
                new Verse("Happiness",
                    "قُلْ بِفَضْلِ اللَّهِ وَبِرَحْمَتِهِ فَبِذَٰلِكَ فَلْيَفْرَحُوا هُوَ خَيْرٌ مِّمَّا يَجْمَعُونَ",
                    "Say, \"In the bounty of Allah and in His mercy - in that let them rejoice; it is better than what they accumulate.\"",
                    "Quran 10:58", Arrays.asList("joy", "mercy", "bounty")),

                new Verse("Sadness",
                    "فَإِنَّ مَعَ الْعُسْرِ يُسْرًا إِنَّ مَعَ الْعُسْرِ يُسْرًا",
                    "For indeed, with hardship [will be] ease. Indeed, with hardship [will be] ease.",
                    "Quran 94:5-6", Arrays.asList("comfort", "difficulty", "ease", "hardship")),
                new Verse("Sadness",
                    "وَلَا تَيْأَسُوا مِن رَّوْحِ اللَّهِ ۖ إِنَّهُ لَا يَيْأَسُ مِن رَّوْحِ اللَّهِ إِلَّا الْقَوْمُ الْكَافِرُونَ",
                    "And never give up hope of Allah's Mercy. Certainly no one despairs of Allah's Mercy, except the people who disbelieve.",
                    "Quran 12:87", Arrays.asList("hope", "mercy", "despair")),

                new Verse("Gratitude",
                    "وَلَقَدْ آتَيْنَاكَ سَبْعًا مِّنَ الْمَثَانِي وَالْقُرْآنَ الْعَظِيمَ",
                    "And We have certainly given you, [O Muhammad], seven of the often-repeated [verses] and the great Qur'an.",
                    "Quran 15:87", Collections.singletonList("blessings")),
                new Verse("Gratitude",
                    "لَئِن شَكَرْتُمْ لَأَزِيدَنَّكُمْ ۖ وَلَئِن كَفَرْتُمْ إِنَّ عَذَابِي لَشَدِيدٌ",
                    "If you are grateful, I will surely increase you [in favor]; but if you deny, indeed, My punishment is severe.",
                    "Quran 14:7", Collections.singletonList("thanks")),

                new Verse("Patience",
                    "يَا أَيُّهَا الَّذِينَ آمَنُوا اسْتَعِينُوا بِالصَّبْرِ وَالصَّلَاةِ ۚ إِنَّ اللَّهَ مَعَ الصَّابِرِينَ",
                    "O you who have believed, seek help through patience and prayer. Indeed, Allah is with the patient.",
                    "Quran 2:153", Arrays.asList("sabr", "prayer")),
                new Verse("Patience",
                    "وَلَنَبْلُوَنَّكُم بِشَيْءٍ مِّنَ الْخَوْفِ وَالْجُوعِ وَنَقْصٍ مِّنَ الْأَمْوَالِ وَالْأَنفُسِ وَالثَّمَرَاتِ ۗ وَبَشِّرِ الصَّابِرِينَ",
                    "And We will surely test you with something of fear and hunger and a loss of wealth and lives and fruits, but give good tidings to the patient,",
                    "Quran 2:155", Arrays.asList("test", "trial")),

                new Verse("Strength",
                    "اللَّهُ لَا إِلَٰهَ إِلَّا هُوَ الْحَيُّ الْقَيُّومُ ۚ لَا تَأْخُذُهُ سِنَةٌ وَلَا نَوْمٌ ۚ",
                    "Allah - there is no deity except Him, the Ever-Living, the Sustainer of [all] existence. Neither drowsiness overtakes Him nor sleep.",
                    "Quran 2:255 (Ayat al-Kursi part)", Collections.singletonList("power")),
                new Verse("Strength",
                    "وَمَن يَتَوَكَّلْ عَلَى اللَّهِ فَهُوَ حَسْبُهُ ۚ إِنَّ اللَّهَ بَالِغُ أَمْرِهِ ۚ قَدْ جَعَلَ اللَّهُ لِكُلِّ شَيْءٍ قَدْرًا",
                    "And whoever relies upon Allah - then He is sufficient for him. Indeed, Allah will accomplish His purpose. Allah has already set for everything a [decreed] extent.",
                    "Quran 65:3", Arrays.asList("trust", "reliance")),

                new Verse("Guidance",
                    "إِيَّاكَ نَعْبُدُ وَإِيَّاكَ نَسْتَعِينُ ﴿٥﴾ اهْدِنَا الصِّرَاطَ الْمُسْتَقِيمَ ﴿٦﴾",
                    "You alone we worship; and You alone we ask for help. Guide us to the straight path.",
                    "Quran 1:5-6", Collections.singletonList("direction")),
                new Verse("Guidance",
                    "وَمَن يُضْلِلِ اللَّهُ فَمَا لَهُ مِنْ هَادٍ",
                    "And he whom Allah leaves astray - there is no guide for him.",
                    "Quran 39:23", Collections.singletonList("hidayah")),

                new Verse("Forgiveness",
                    "وَمَن يَغْفِرُ الذُّنُوبَ إِلَّا اللَّهُ",
                    "And who can forgive sins except Allah?",
                    "Quran 3:135", Collections.singletonList("maghfirah")),
                new Verse("Forgiveness",
                    "قُلْ يَا عِبَادِيَ الَّذِينَ أَسْرَفُوا عَلَىٰ أَنفُسِهِمْ لَا تَقْنَطُوا مِن رَّحْمَةِ اللَّهِ ۚ إِنَّ اللَّهَ يَغْفِرُ الذُّنُوبَ جَمِيعًا ۚ إِنَّهُ هُوَ الْغَفُورُ الرَّحِيمُ",
                    "Say, \"O My servants who have transgressed against themselves [by sinning], do not despair of the mercy of Allah. Indeed, Allah forgives all sins. Indeed, it is He who is the Forgiving, the Merciful.\"",
                    "Quran 39:53", Arrays.asList("mercy", "repentance")),

                new Verse("Hope",
                    "لَا تَقْنَطُوا مِن رَّحْمَةِ اللَّهِ",
                    "Do not despair of the mercy of Allah.",
                    "Quran 39:53", Collections.singletonList("optimism")),
                new Verse("Hope",
                    "وَهُوَ الَّذِي يُنَزِّلُ الْغَيْثَ مِن بَعْدِ مَا قَنَطُوا وَيَنشُرُ رَحْمَتَهُ ۚ وَهُوَ الْوَلِيُّ الْحَمِيدُ",
                    "And He is the one who sends down the rain after they had despaired and spreads His mercy. And He is the Protector, the Praiseworthy.",
                    "Quran 42:28", Arrays.asList("rain", "mercy")),

                new Verse("Peace",
                    "وَلِلَّهِ غَيْبُ السَّمَاوَاتِ وَالْأَرْضِ وَإِلَيْهِ يُرْجَعُ الْأَمْرُ كُلُّهُ فَاعْبُدْهُ وَتَوَكَّلْ عَلَيْهِ ۚ وَمَا رَبُّكَ بِغَافِلٍ عَمَّا تَعْمَلُونَ",
                    "And to Allah belong the unseen [aspects] of the heavens and the earth and to Him will all affairs be returned. So worship Him and rely upon Him. And your Lord is not unaware of that which you do.",
                    "Quran 11:123", Collections.singletonList("tranquility")),
                new Verse("Peace",
                    "الَّذِينَ آمَنُوا وَتَطْمَئِنُّ قُلُوبُهُم بِذِكْرِ اللَّهِ ۗ أَلَا بِذِكْرِ اللَّهِ تَطْمَئِنُّ الْقُلُوبُ",
                    "Those who have believed and whose hearts are assured by the remembrance of Allah. Unquestionably, by the remembrance of Allah hearts are assured.",
                    "Quran 13:28", Arrays.asList("dhikr", "remembrance"))
            );
            verseRepository.saveAll(sampleVerses);
            System.out.println("Verses by Mood database initialized with " + sampleVerses.size() + " verses.");
        } else {
            System.out.println("Verses by Mood database already exists. Skipping initialization.");
        }
    }
}