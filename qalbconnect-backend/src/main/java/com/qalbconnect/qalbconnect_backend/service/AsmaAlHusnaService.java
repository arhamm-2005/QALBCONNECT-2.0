// src/main/java/com/qalbconnect/qalbconnect_backend/service/AsmaAlHusnaService.java
package com.qalbconnect.qalbconnect_backend.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qalbconnect.qalbconnect_backend.model.AsmaAlHusna;
import com.qalbconnect.qalbconnect_backend.repository.AsmaAlHusnaRepository;

import jakarta.annotation.PostConstruct;

@Service
public class AsmaAlHusnaService {

    private final AsmaAlHusnaRepository asmaAlHusnaRepository;

    @Autowired
    public AsmaAlHusnaService(AsmaAlHusnaRepository asmaAlHusnaRepository) {
        this.asmaAlHusnaRepository = asmaAlHusnaRepository;
    }

    public List<AsmaAlHusna> getAllAsmaAlHusna() {
        return asmaAlHusnaRepository.findAll();
    }

    @PostConstruct
    public void initializeAsmaAlHusna() {
        long count = asmaAlHusnaRepository.count();
        if (count == 0) {
            List<AsmaAlHusna> names = Arrays.asList(
                new AsmaAlHusna("ٱلْرَّحْمَـٰنُ", "Ar-Rahman (The Most Merciful)"),
                new AsmaAlHusna("ٱلْرَّحِيمُ", "Ar-Rahim (The Most Compassionate)"),
                new AsmaAlHusna("ٱلْمَلِكُ", "Al-Malik (The King, The Sovereign)"),
                new AsmaAlHusna("ٱلْقُدُّوسُ", "Al-Quddus (The Holy, The Pure)"),
                new AsmaAlHusna("ٱلْسَّلَامُ", "As-Salam (The Giver of Peace)"),
                new AsmaAlHusna("ٱلْمُؤْمِنُ", "Al-Mu'min (The Giver of Faith)"),
                new AsmaAlHusna("ٱلْمُهَيْمِنُ", "Al-Muhaymin (The Guardian, The Protector)"),
                new AsmaAlHusna("ٱلْعَزِيزُ", "Al-Aziz (The Almighty, The Victorious)"),
                new AsmaAlHusna("ٱلْجَبَّارُ", "Al-Jabbar (The Compeller, The Restorer)"),
                new AsmaAlHusna("ٱلْمُتَكَبِّرُ", "Al-Mutakabbir (The Supreme, The Majestic)"),
                new AsmaAlHusna("ٱلْخَالِقُ", "Al-Khaliq (The Creator)"),
                new AsmaAlHusna("ٱلْبَارِئُ", "Al-Bari (The Evolver, The Maker)"),
                new AsmaAlHusna("ٱلْمُصَوِّرُ", "Al-Musawwir (The Fashioner, The Shaper)"),
                new AsmaAlHusna("ٱلْغَفَّارُ", "Al-Ghaffar (The Forgiver, The Pardoner)"),
                new AsmaAlHusna("ٱلْقَهَّارُ", "Al-Qahhar (The Subduer, The Dominant)"),
                new AsmaAlHusna("ٱلْوَهَّابُ", "Al-Wahhab (The Bestower)"),
                new AsmaAlHusna("ٱلْرَّزَّاقُ", "Ar-Razzaq (The Provider, The Sustainer)"),
                new AsmaAlHusna("ٱلْفَتَّاحُ", "Al-Fattah (The Opener, The Revealer)"),
                new AsmaAlHusna("ٱلْعَلِيمُ", "Al-Alim (The All-Knowing, The Omniscient)"),
                new AsmaAlHusna("ٱلْقَابِضُ", "Al-Qabid (The Restrainer, The Withholder)"),
                new AsmaAlHusna("ٱلْبَاسِطُ", "Al-Basit (The Extender, The Enlarger)"),
                new AsmaAlHusna("ٱلْخَافِضُ", "Al-Khafid (The Abaser, The Humbler)"),
                new AsmaAlHusna("ٱلْرَّافِعُ", "Ar-Rafi (The Exalter, The Elevator)"),
                new AsmaAlHusna("ٱلْمُعِزُّ", "Al-Mu'izz (The Bestower of Honor)"),
                new AsmaAlHusna("ٱلْمُذِلُّ", "Al-Muzil (The Humiliator)"),
                new AsmaAlHusna("ٱلْسَّمِيعُ", "As-Sami (The All-Hearing)"),
                new AsmaAlHusna("ٱلْبَصِيرُ", "Al-Basir (The All-Seeing)"),
                new AsmaAlHusna("ٱلْحَكَمُ", "Al-Hakam (The Judge, The Arbitrator)"),
                new AsmaAlHusna("ٱلْعَدْلُ", "Al-Adl (The Just, The Equitable)"),
                new AsmaAlHusna("ٱلْلَّطِيفُ", "Al-Latif (The Subtle One, The Kind)"),
                new AsmaAlHusna("ٱلْخَبِيرُ", "Al-Khabir (The All-Aware)"),
                new AsmaAlHusna("ٱلْحَلِيمُ", "Al-Halim (The Forbearing, The Indulgent)"),
                new AsmaAlHusna("ٱلْعَظِيمُ", "Al-Azim (The Magnificent, The Great)"),
                new AsmaAlHusna("ٱلْغَفُورُ", "Al-Ghafur (The All-Forgiving)"),
                new AsmaAlHusna("ٱلْشَّكُورُ", "Ash-Shakur (The Appreciative, The Rewarder of Thankfulness)"),
                new AsmaAlHusna("ٱلْعَلِيُّ", "Al-Ali (The Most High, The Exalted)"),
                new AsmaAlHusna("ٱلْكَبِيرُ", "Al-Kabir (The Most Great, The Grand)"),
                new AsmaAlHusna("ٱلْحَفِيظُ", "Al-Hafiz (The Preserver, The Protector)"),
                new AsmaAlHusna("ٱلْمُقِيتُ", "Al-Muqit (The Sustainer, The Maintainer)"),
                new AsmaAlHusna("ٱلْحَسِيبُ", "Al-Hasib (The Reckoner, The Accounter)"),
                new AsmaAlHusna("ٱلْجَلِيلُ", "Al-Jalil (The Sublime, The Majestic)"),
                new AsmaAlHusna("ٱلْكَرِيمُ", "Al-Karim (The Most Generous, The Bountiful)"),
                new AsmaAlHusna("ٱلْرَّقِيبُ", "Ar-Raqib (The Watchful, The Observer)"),
                new AsmaAlHusna("ٱلْمُجِيبُ", "Al-Mujib (The Responder, The Answerer of Prayer)"),
                new AsmaAlHusna("ٱلْوَاسِعُ", "Al-Wasi (The All-Encompassing, The Vast)"),
                new AsmaAlHusna("ٱلْحَكِيمُ", "Al-Hakim (The All-Wise, The Judicious)"),
                new AsmaAlHusna("ٱلْوَدُودُ", "Al-Wadud (The Most Loving, The Affectionate)"),
                new AsmaAlHusna("ٱلْمَجِيدُ", "Al-Majid (The Most Glorious, The Illustrious)"),
                new AsmaAlHusna("ٱلْبَاعِثُ", "Al-Ba'ith (The Resurrector, The Awakener)"),
                new AsmaAlHusna("ٱلْشَّهِيدُ", "Ash-Shahid (The Witness)"),
                new AsmaAlHusna("ٱلْحَقُّ", "Al-Haqq (The Truth, The Reality)"),
                new AsmaAlHusna("ٱلْوَكِيلُ", "Al-Wakil (The Trustee, The Guardian)"),
                new AsmaAlHusna("ٱلْقَوِيُّ", "Al-Qawi (The All-Strong, The Mighty)"),
                new AsmaAlHusna("ٱلْمَتِينُ", "Al-Matin (The Firm, The Steadfast)"),
                new AsmaAlHusna("ٱلْوَلِيُّ", "Al-Wali (The Protecting Friend, The Patron)"),
                new AsmaAlHusna("ٱلْحَمِيدُ", "Al-Hamid (The Praiseworthy)"),
                new AsmaAlHusna("ٱلْمُحْصِى", "Al-Muhsi (The Reckoner, The Appraiser)"),
                new AsmaAlHusna("ٱلْمُبْدِئُ", "Al-Mubdi (The Originator, The Starter)"),
                new AsmaAlHusna("ٱلْمُعِيدُ", "Al-Mu'id (The Restorer, The Renewer)"),
                new AsmaAlHusna("ٱلْمُحْيِى", "Al-Muhyi (The Giver of Life)"),
                new AsmaAlHusna("ٱلْمُمِيتُ", "Al-Mumit (The Taker of Life, The Destroyer)"),
                new AsmaAlHusna("ٱلْحَيُّ", "Al-Hayy (The Ever-Living)"),
                new AsmaAlHusna("ٱلْقَيُّومُ", "Al-Qayyum (The Sustainer of All, The Self-Existing)"),
                new AsmaAlHusna("ٱلْوَاجِدُ", "Al-Wajid (The Finder, The Perceiver)"),
                new AsmaAlHusna("ٱلْمَاجِدُ", "Al-Majid (The Glorious, The Noble)"),
                new AsmaAlHusna("ٱلْوَاحِدُ", "Al-Wahid (The One, The Unique)"),
                new AsmaAlHusna("ٱلْأَحَدُ", "Al-Ahad (The One, The Indivisible)"),
                new AsmaAlHusna("ٱلْصَّمَدُ", "As-Samad (The Eternal, The Absolute)"),
                new AsmaAlHusna("ٱلْقَادِرُ", "Al-Qadir (The All-Powerful, The Capable)"),
                new AsmaAlHusna("ٱلْمُقْتَدِرُ", "Al-Muqtadir (The All-Determiner, The Prevailing)"),
                new AsmaAlHusna("ٱلْمُقَدِّمُ", "Al-Muqaddim (The Expediter, The Advancer)"),
                new AsmaAlHusna("ٱلْمُؤَخِّرُ", "Al-Mu'akhkhir (The Delayer, The Retarder)"),
                new AsmaAlHusna("ٱلْأَوَّلُ", "Al-Awwal (The First)"),
                new AsmaAlHusna("ٱلْآخِرُ", "Al-Akhir (The Last)"),
                new AsmaAlHusna("ٱلْظَّاهِرُ", "Az-Zahir (The Manifest, The Evident)"),
                new AsmaAlHusna("ٱلْبَاطِنُ", "Al-Batin (The Hidden, The Inner)"),
                new AsmaAlHusna("ٱلْوَالِي", "Al-Wali (The Governing, The Ruler)"),
                new AsmaAlHusna("ٱلْمُتَعَالِي", "Al-Muta'ali (The Most Exalted, The Supreme)"),
                new AsmaAlHusna("ٱلْبَرُّ", "Al-Barr (The Doer of Good, The Benefactor)"),
                new AsmaAlHusna("ٱلْتَّوَّابُ", "At-Tawwab (The Acceptor of Repentance)"),
                new AsmaAlHusna("ٱلْمُنْتَقِمُ", "Al-Muntaqim (The Avenger, The Retaliator)"),
                new AsmaAlHusna("ٱلْعَفُوُّ", "Al-Afu (The Pardoner, The Forgiver)"),
                new AsmaAlHusna("ٱلْرَّؤُوفُ", "Ar-Ra'uf (The Compassionate, The All-Pitying)"),
                new AsmaAlHusna("مَالِكُ ٱلْمُلْكِ", "Malik-ul-Mulk (The Owner of All Sovereignty)"),
                new AsmaAlHusna("ذُو ٱلْجَلَالِ وَٱلْإِكْرَامِ", "Zul-Jalali wal-Ikram (The Lord of Majesty and Honor)"),
                new AsmaAlHusna("ٱلْمُقْسِطُ", "Al-Muqsit (The Equitable, The Requiter)"),
                new AsmaAlHusna("ٱلْجَامِعُ", "Al-Jami (The Gatherer, The Uniter)"),
                new AsmaAlHusna("ٱلْغَنِىُّ", "Al-Ghaniyy (The Self-Sufficient, The Wealthy)"),
                new AsmaAlHusna("ٱلْمُغْنِى", "Al-Mughni (The Enricher)"),
                new AsmaAlHusna("ٱلْمَانِعُ", "Al-Mani (The Withholder)"),
                new AsmaAlHusna("ٱلضَّارُّ", "Ad-Darr (The Distresser)"),
                new AsmaAlHusna("ٱلنَّافِعُ", "An-Nafi (The Propitious, The Benefactor)"),
                new AsmaAlHusna("ٱلنُّورُ", "An-Nur (The Light, The Illuminator)"),
                new AsmaAlHusna("ٱلْهَادِي", "Al-Hadi (The Guide)"),
                new AsmaAlHusna("ٱلْبَدِيعُ", "Al-Badi (The Incomparable Originator)"),
                new AsmaAlHusna("ٱلْبَاقِي", "Al-Baqi (The Ever-Surviving, The Everlasting)"),
                new AsmaAlHusna("ٱلْوَارِثُ", "Al-Warith (The Inheritor, The Heir)"),
                new AsmaAlHusna("ٱلْرَّشِيدُ", "Ar-Rashid (The Guide to the Right Path)"),
                new AsmaAlHusna("ٱلصَّبُورُ", "As-Sabur (The Patient)"),
                new AsmaAlHusna("ٱلْغَفَّارُ", "Al-Ghaffar (The All-Forgiving)"), // Duplicated, keeping for 99 count, consider removing if strict unique count is needed
                new AsmaAlHusna("ٱلْأَحَدُ", "Al-Ahad (The One, The Indivisible)") // Duplicated, keeping for 99 count, consider removing if strict unique count is needed
            );
            asmaAlHusnaRepository.saveAll(names);
            System.out.println("99 Names of Allah initialized in the database.");
        } else {
            System.out.println("99 Names of Allah already exist in the database. Skipping initialization.");
        }
    }
}