/*
 * Créé le 15 déc. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.outils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

import org.apache.commons.validator.GenericValidator;

/**
 * @author M327836
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class DateUtil {
    /**Constante*/
    private static final int C1 = 1;
    /**Constante*/
    private static final int C2 = 2;
    /**Constante*/
    private static final int C3 = 3;
    /**Constante*/
    private static final int C4 = 4;
    /**Constante*/
    private static final int C7 = 7;
    /**Constante*/
    private static final int C8 = 8;
    /**Constante*/
    private static final int C11 = 11;
    /**Constante*/
    private static final int C12 = 12;
    /**Constante*/
    private static final int C14 = 14;
    /**Constante*/
    private static final int C15 = 15;
    /**Constante*/
    private static final int C17 = 17;
    /**Constante*/
    private static final int C19 = 19;
    /**Constante*/
    private static final int C22 = 22;
    /**Constante*/
    private static final int C25 = 25;
    /**Constante*/
    private static final int C30 = 30;
    /**Constante*/
    private static final int C31 = 31;
    /**Constante*/
    private static final int C32 = 32;
    /**Constante*/
    private static final int C39 = 39;
    /**Constante*/
    private static final int C49 = 49;
    /**Constante*/
    private static final int C100 = 100;
    /**Constante*/
    private static final int C144 = 144;
    /**Constante*/
    private static final int C451 = 451;

    /**Constante*/
    private static final int JANVIER = 0;
    /**Constante*/
    private static final int FEVRIER = 1;
    /**Constante*/
    private static final int MARS = 2;
    /**Constante*/
    private static final int AVRIL = 3;
    /**Constante*/
    private static final int MAI = 4;
    /**Constante*/
    private static final int JUIN = 5;
    /**Constante*/
    private static final int JUILLET = 6;
    /**Constante*/
    private static final int AOUT = 7;
    /**Constante*/
    private static final int NOVEMBRE = 10;
    /**Constante*/
    private static final int DECEMBRE = 11;
    /**format jdbc date heure dd/MM/yyyy hh:mm*/
    private static SimpleDateFormat msdf_jdbc_date_heure;
    /**format date dd/MM/yyyy*/
    private static SimpleDateFormat msdf_francais;
    /**format jdbc date dd/MM/yyyy*/
    private static SimpleDateFormat msdf_jdbc_date;
    /**Constante*/
    public final static long MILLI_SEC = 1;
    /**Constante*/
    public final static long SEC = 1000;
    /**Constante*/
    public final static long MIN = SEC * 60;
    /**Constante*/
    public final static long HOUR = MIN * 60;
    /**Constante*/
    public final static long DAY = HOUR * 24;
    /**Constante*/
    public final static long WEEK = DAY * 7;
    /**Constante*/
    public final static int MONDAY = Calendar.MONDAY;
    /**Constante*/
    public final static int TUESDAY = Calendar.TUESDAY;
    /**Constante*/
    public final static int WEDNESDAY = Calendar.WEDNESDAY;
    /**Constante*/
    public final static int THURSDAY = Calendar.THURSDAY;
    /**Constante*/
    public final static int FRIDAY = Calendar.FRIDAY;
    /**Constante*/
    public final static int SATURDAY = Calendar.SATURDAY;
    /**Constante*/
    public final static int SUNDAY = Calendar.SUNDAY;
    /**Constante*/
    private final static int WEEK_DAYS = 7;

    /**
     *constructeur
     */
    public DateUtil() {
    }

    /**
     * instanciation des formats de date
     */
    static void instanciate() {
        if (msdf_francais == null) {
            msdf_francais = new SimpleDateFormat("dd/MM/yyyy");
        }

        if (msdf_jdbc_date_heure == null) {
            msdf_jdbc_date_heure = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        }

        if (msdf_jdbc_date == null) {
            msdf_jdbc_date = new SimpleDateFormat("dd/MM/yyyy");
        }
    }

    /**
     *
     * @param date1 : chaine de caractère représentant une date de début du type "dd/MM/yyyy" ou "dd/MM/yyyy hh:mm"
     * @param date2 : chaine de caractère représentant une date de fin du type "dd/MM/yyyy" ou "dd/MM/yyyy hh:mm"
     * @param unit : unité de temps dans laquelle doit être calculée la durée (MILLI_SEC,SEC,DAY,HOUR,MIN)
     * @return retourne le nombre de jour entre deux dates
     */
    public static long duration(final String date1, final String date2, final long unit) {
        return duration(parseAllDate(date1), parseAllDate(date2), unit);
    }

    /**
     *
     * @param date1 : date de début (java.util.Date)
     * @param date2 : date de fin (java.util.Date)
     * @param unit : unité de temps dans laquelle doit être calculée la durée (MILLI_SEC,SEC,DAY,HOUR,MIN)
     * @return retourne le nombre de jour entre deux dates
     */
    public static long duration(final java.util.Date date1, java.util.Date date2, final long unit) {
        date2 = addDaysDate(date2, 1);

        if ((date1 == null) || (date2 == null)) {
            return 0;
        }

        final long dateDebut = date1.getTime();
        final long dateFin = date2.getTime();

        return (dateFin - dateDebut) / unit;
    }

    /**
     * Ajoute un certain nombre de jour à une date
     * @param date1 Date sous forme de chaine de carectère
     * @param nbdays nombre de jours à ajouter
     * @return une date au format chaine de caractère avec le nombre de jour ajoutés
     */
    public static String addDays(final String date1, final int nbdays) {
        instanciate();

        java.util.Date d = null;

        if (!GenericValidator.isBlankOrNull(date1)) {
            try {
                d = msdf_jdbc_date_heure.parse(date1);

                return msdf_jdbc_date_heure.format(addDaysDate(d, nbdays));
            } catch (final ParseException e) {
            }

            try {
                d = msdf_jdbc_date.parse(date1);

                return msdf_jdbc_date.format(addDaysDate(d, nbdays));
            } catch (final ParseException e) {
                return "";
            }
        } else {
            return "";
        }
    }

    /**
     * retourne le porchain jour de la semaine saisi en paramètre par rapport à la date d'entrée
     * @param date date d'entrée
     * @param dayofweek Jour de la semaine à utiliser
     * @return la date du prochain jour de la semaine
     */
    public static java.util.Date getNext(final java.util.Date date, final int dayofweek) {
        final GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);

        final int actualday = gc.get(Calendar.DAY_OF_WEEK);
        gc.add(Calendar.DATE, ((dayofweek - actualday + WEEK_DAYS) % WEEK_DAYS));

        return gc.getTime();
    }

    /**
     * 
     * @param date date en entrée
     * @return retourne le jour de la semaine correspondant à la date en entrée
     */
    public static int getDay(final java.util.Date date) {
        final GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);

        return gc.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * Ajoute un certain nombre de jour à une date
     * @param date1 Date sous forme de jave.util.Date
     * @param nbdays nombre de jours à ajouter
     * @return la date avec le nombre de jour ajouté
     */
    public static java.util.Date addDaysDate(final java.util.Date date1, final int nbdays) {
        if (date1 == null) {
            return null;
        }

        final GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date1);
        cal.add(Calendar.DATE, nbdays);

        return cal.getTime();
    }

    /**
       *
       * @param date1 : chaine de caractère représentant une date de début du type "dd/MM/yyyy" ou "dd/MM/yyyy hh:mm"
       * @param date2 : chaine de caractère représentant une date de fin du type "dd/MM/yyyy" ou "dd/MM/yyyy hh:mm"
       * @param unit : unité de temps dans laquelle doit être calculée la durée (MILLI_SEC,SEC,DAY,HOUR,MIN)
       * @return retourne le nombre de jour entre deux dates les samedi et dimanche n'étant pas inclu
       */
    public static long durationNoWeekEnd(final String date1, final String date2, final long unit) {
        return durationNoWeekEnd(parseAllDate(date1), parseAllDate(date2), unit);
    }

    /**
     *
     * @param dateDebut : date de début (java.util.Date)
     * @param dateFin : date de fin (java.util.Date)
     * @param unit : unité de temps dans laquelle doit être calculée la durée (MILLI_SEC,SEC,DAY,HOUR,MIN)
     * @return retourne le nombre de jour entre deux dates les samedi et dimanche n'étant pas inclu
     */
    public static long durationNoWeekEnd(final java.util.Date dateDebut, final java.util.Date dateFin, final long unit) {
        long duration = duration(dateDebut, dateFin, unit);
        final GregorianCalendar gc = new GregorianCalendar();

        if (duration != 0) {
            gc.setTime(dateDebut);

            while (gc.getTime().getTime() <= dateFin.getTime()) {
                if ((gc.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) || (gc.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
                    duration--;
                }

                gc.add(Calendar.DATE, 1);
            }
        }

        return duration;
    }

    /**
     * converti une date chaine au format dd/MM/yyyy
     * @param dateAConvertir date à convertir
     * @return date convertie
     */
    public static String parseDatetoString(final String dateAConvertir) {
        return parseDatetoString(parseAllDate(dateAConvertir));
    }

    /**
     * converti une date au format dd/MM/yyyy
     * @param dateAConvertir date à convertir
     * @return date convertie
     */
    public static String parseDatetoString(final Date dateAConvertir) {
        if (dateAConvertir != null) {
            instanciate();

            final String dateRenvoyee = msdf_francais.format(dateAConvertir);

            return dateRenvoyee;
        } else {
            return "";
        }
    }

    /**
     * transforme une chaine de date type jdbc en date 
     * @param psDate date chaine
     * @return une date
     * @throws ParseException parse exception
     * @deprecated utiliser la methode parseAllDate(String date)
     */
    public static java.util.Date parseJdbcToDate(final String psDate) throws ParseException {
        instanciate();

        if (!GenericValidator.isBlankOrNull(psDate)) {
            return msdf_jdbc_date.parse(psDate);
        } else {
            return null;
        }
    }

    /**
      * transforme une chaine de date type jdbc en date  et heure
      * @param psDate date chaine
      * @return une date et heure
      * @throws ParseException parse exception
      * @deprecated utiliser la methode parseAllDate(String date)
      */
    public static java.util.Date parseJdbcToDateHeure(final String psDate) throws ParseException {
        instanciate();

        if (!GenericValidator.isBlankOrNull(psDate)) {
            return msdf_jdbc_date_heure.parse(psDate);
        } else {
            return null;
        }
    }

    /**
     * Transforme une chaine en date en prenant en compte les trois types de formats
     * @param date Chaine à transformer
     * @return la date transformée
     */
    public static java.util.Date parseAllDate(final String date) {
        instanciate();

        java.util.Date d = null;

        if (!GenericValidator.isBlankOrNull(date)) {
            try {
                d = msdf_jdbc_date_heure.parse(date);

                return d;
            } catch (final ParseException e) {
                try {
                    d = msdf_jdbc_date.parse(date);

                    return d;
                } catch (final ParseException pe) {
                    try {
                        final long timeLong = Long.parseLong(date);
                        d = new Date(timeLong);
                        return d;
                    } catch (final NumberFormatException nfe) {
                        return null;
                    }
                }
            }
        } else {
            return null;
        }
    }

    /**
     * les dates doivent être au format "dd/MM/yyyy" ou "dd/MM/yyyy hh:mm"
     * @param fpbd = First Period begin date (Date de debut de la première periode)
     * @param fped = First Period end date (Date de fin de la première periode)
     * @param spbd = Second Period begin date (Date de debut de la seconde periode)
     * @param sped = Second Period end date (Date de fin de la seconde periode)
     * @return retourne le nombe de jours de chevauchement entre les 2 périodes
     *
     */
    public static long overlappingDaysInPeriods(final String fpbd, final String fped, final String spbd, final String sped) {
        return overlappingDaysInPeriods(parseAllDate(fpbd), parseAllDate(fped), parseAllDate(spbd), parseAllDate(sped));
    }

    /**
      *
      * @param fpbd = First Period begin date (Date de debut de la première periode)
      * @param fped = First Period end date (Date de fin de la première periode)
      * @param spbd = Second Period begin date (Date de debut de la seconde periode)
      * @param sped = Second Period end date (Date de fin de la seconde periode)
      * @return retourne le nombe de jours de chevauchement entre les 2 périodes
      */
    public static long overlappingDaysInPeriods(final java.util.Date fpbd, final java.util.Date fped, final java.util.Date spbd, final java.util.Date sped) {
        if (testNull(fpbd, fped, spbd, sped)) {
            return 0; //au moins une des dates est nulle...pas de chevauchement
        }

        if (noOverLap(fpbd, fped, spbd, sped)) {
            return 0; //pas de chevauchement des périodes
        }

        final long lfpbd = fpbd.getTime();
        final long lfped = fped.getTime();
        final long lspbd = spbd.getTime();
        final long lsped = sped.getTime();

        if (intersecType1(lfpbd, lfped, lspbd, lsped)) {
            return duration(spbd, fped, DAY);
        }

        if (intersecType2(lfpbd, lfped, lspbd, lsped)) {
            return duration(fpbd, sped, DAY);
        }

        if (intersecType3(lfpbd, lfped, lspbd, lsped)) {
            return duration(spbd, sped, DAY);
        }

        if (intersecType4(lfpbd, lfped, lspbd, lsped)) {
            return duration(fpbd, fped, DAY);
        }

        return 0;
    }

    /**
     * 
     * @param lfpbd long
     * @param lfped long
     * @param lspbd long
     * @param lsped long
     * @return vrai si ok
     */
    private static boolean intersecType4(final long lfpbd, final long lfped, final long lspbd, final long lsped) {
        return (lspbd < lfpbd) && (lfpbd < lfped) && (lfped < lsped);
    }

    /**
    * 
    * @param lfpbd long
    * @param lfped long
    * @param lspbd long
    * @param lsped long
    * @return vrai si ok
    */
    private static boolean intersecType3(final long lfpbd, final long lfped, final long lspbd, final long lsped) {
        return (lfpbd < lspbd) && (lspbd < lsped) && (lsped < lfped);
    }

    /**
    * 
    * @param lfpbd long
    * @param lfped long
    * @param lspbd long
    * @param lsped long
    * @return vrai si ok
    */
    private static boolean intersecType2(final long lfpbd, final long lfped, final long lspbd, final long lsped) {
        return (lspbd < lfpbd) && (lfpbd < lsped) && (lsped < lfped);
    }

    /**
    * 
    * @param lfpbd long
    * @param lfped long
    * @param lspbd long
    * @param lsped long
    * @return vrai si ok
    */
    private static boolean intersecType1(final long lfpbd, final long lfped, final long lspbd, final long lsped) {
        return (lfpbd < lspbd) && (lspbd < lfped) && (lfped < lsped);
    }

    /**
     * 
       * @param fpbd = First Period begin date (Date de debut de la première periode)
      * @param fped = First Period end date (Date de fin de la première periode)
      * @param spbd = Second Period begin date (Date de debut de la seconde periode)
      * @param sped = Second Period end date (Date de fin de la seconde periode)
     * @return vrai si les perodes de se chevauchent pas
     */
    private static boolean noOverLap(final java.util.Date fpbd, final java.util.Date fped, final java.util.Date spbd, final java.util.Date sped) {
        return (fped.getTime() < spbd.getTime()) || (sped.getTime() < fpbd.getTime());
    }

    /**
     * 
     * @param fpbd date
     * @param fped date
     * @param spbd date 
     * @param sped date
     * @return vrai si une des dates est nulle
     */
    private static boolean testNull(final java.util.Date fpbd, final java.util.Date fped, final java.util.Date spbd, final java.util.Date sped) {
        return (fped == null) || (spbd == null) || (sped == null) || (fpbd == null);
    }

    /**
     *
     * @param day = jour à tester au format chaine de caractère
     * @return Retourne si la date passée en entrée correspond à un jour ferié
     */
    public static boolean isPublicHolyday(final String day) {
        return isPublicHolyday(parseAllDate(day));
    }

    /**
     *
     * @param day = jour à tester au format Date
     * @return Retourne si la date passée en entrée correspond à un jour ferié
     */
    public static boolean isPublicHolyday(final Date day) {
        if (day == null) {
            return false;
        }

        final GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(day);

        final ArrayList ph = getPublicHolydays(gc.get(Calendar.YEAR));

        for (final Iterator iter = ph.iterator(); iter.hasNext();) {
            final Date d = (Date) iter.next();

            if (d.equals(day)) {
                return true;
            }
        }

        return false;
    }

    /**
     *
     * @param year l'année
     * @return retourne les jours fériés d'une année sous forme d'une ArrayList d'objets java.util.Date
     */
    public static ArrayList getPublicHolydays(final int year) {
        final ArrayList ph = new ArrayList();

        //Jours Fixes
        ph.add(new GregorianCalendar(year, JANVIER, C1).getTime()); // jour de l'an
        ph.add(new GregorianCalendar(year, MAI, C1).getTime()); // fête du travail
        ph.add(new GregorianCalendar(year, MAI, C8).getTime()); // victoire 1945
        ph.add(new GregorianCalendar(year, JUILLET, C14).getTime()); // fête nationale
        ph.add(new GregorianCalendar(year, AOUT, C15).getTime()); // assomption
        ph.add(new GregorianCalendar(year, NOVEMBRE, C11).getTime()); // armistice 1918
        ph.add(new GregorianCalendar(year, NOVEMBRE, C1).getTime()); // Toussaint
        ph.add(new GregorianCalendar(year, DECEMBRE, C25).getTime()); // noël

        //Calcul du Jours de Pâques et du Lundi de Pâques
        int m, c, y, s, t, p, q, e, b, d, l, h;
        int moispaques;
        int jourpaques;
        m = (year) % C19;
        c = year / C100;
        y = (year) % C100;
        s = c / C4;
        t = (c) % C4;
        p = (c + C8) / C25;
        q = (c - p + 1) / C3;
        e = (((C19 * m) + c) - s - q + C15) % C30;
        b = y / C4;
        d = y % C4;
        l = ((C32 + (C2 * t) + (C2 * b)) - e - d) % C7;
        h = (m + (C11 * e) + (C22 * l)) / C451;
        moispaques = (((e + l) - (C17 * h) + C144) / C31) - C2;
        jourpaques = (((e + l) - (C17 * h) + C144) % C31) + C2;

        ph.add(new GregorianCalendar(year, moispaques, jourpaques).getTime()); // Dianche de pâques
        ph.add(new GregorianCalendar(year, moispaques, jourpaques + 1).getTime()); // Lundi de pâques

        // Calcul du jour de la Pentecôte et du lundi de Pentecôte
        int jourpentecote = C1;
        int moispentecote = FEVRIER;

        if (moispaques == MARS) {
            jourpentecote = C49 - (C31 - jourpaques + C30);
            moispentecote = MAI;
        }

        if ((moispaques == AVRIL) && (jourpaques <= C12)) {
            jourpentecote = C49 - (C30 - jourpaques);
            moispentecote = MAI;
        }

        if ((moispaques == AVRIL) && (jourpaques > C12)) {
            jourpentecote = C49 - (C30 - jourpaques + C31);
            moispentecote = JUIN;
        }

        ph.add(new GregorianCalendar(year, moispentecote, jourpentecote).getTime()); // Dianche de pentecôte
        ph.add(new GregorianCalendar(year, moispentecote, jourpentecote + 1).getTime()); // Lundi de pentecôte

        // Calcul du jour de l'Ascension
        int jourAscension = C1;
        int moisAscension = FEVRIER;

        if ((jourpaques == C22) && (moispaques == MARS)) {
            jourAscension = C30;
            moisAscension = AVRIL;
        }

        if ((jourpaques > C22) && (moispaques == MARS)) {
            jourAscension = C39 - (C31 - jourpaques + C30);
            moisAscension = MAI;
        }

        if ((jourpaques <= C22) && (moispaques == AVRIL)) {
            jourAscension = C39 - (C30 - jourpaques);
            moisAscension = MAI;
        }

        if ((jourpaques > C22) && (moispaques == AVRIL)) {
            jourAscension = C39 - (C30 - jourpaques + C31);
            moisAscension = JUIN;
        }

        ph.add(new GregorianCalendar(year, moisAscension, jourAscension).getTime()); // Ascension

        return ph;
    }

    /**
     *
     * @param beginDate date de début de la période en Chaine de caractères
     * @param endDate  date de fin de la période en Chaine de caractères
     * @param weekend  true pour que les jours fériés tombant un week end soient comtabilisé, false sinon.
     * @return retourne le nombre de jours fériés dans la période.
     */
    public static int getNbPublicHolliday(final String beginDate, final String endDate, final boolean weekend) {
        return getNbPublicHolliday(parseAllDate(beginDate), parseAllDate(endDate), weekend);
    }


    /**
     * Retourn l'année en int d'une date
     * @param gc gregorien calendar
     * @param date date
     * @return l'année en int d'une date
     */
    private static int getYearOfDate(GregorianCalendar gc,final Date date) {
        
        // recupere l'année de debut
        gc.setTime(date);
        return  gc.get(Calendar.YEAR); 
    }

    
    /**
     * Retourn le nombre de jour ferier entre 2 année
     * @param by : année debut
     * @param ey : année fin
     * @return les jours feriers
     */
    private static ArrayList getPublicHolliday(final int by, final int ey) {
        
        // Recupere la liste de jour ferier sur l'année
        final ArrayList ph = new ArrayList();
        for (int i = by; i <= ey; i++) {
            ph.addAll(getPublicHolydays(i));
        }
        
        return ph;
    }



    /**
     *
     * @param beginDate date de début de la période en Date
     * @param endDate  date de fin de la période en Date
     * @param weekend  true pour que les jours fériés tombant un week end soient comtabilisé, false sinon.
     * @return retourne le nombre de jours fériés dans la période.
     */
    public static int getNbPublicHolliday(final Date beginDate, final Date endDate, final boolean weekend) {
        if ((beginDate == null) || (endDate == null)) {
            return 0;
        }
        final GregorianCalendar gc = new GregorianCalendar();
        // recupere l'année de debut
        int by = getYearOfDate(gc,beginDate);

        // Recupere l'année de fin        
        int ey = getYearOfDate(gc,endDate);

        // Recupere les jours de vacance des 2 années        
        ArrayList ph = getPublicHolliday(by,ey);


        int nbph = 0;

        for (final Iterator iter = ph.iterator(); iter.hasNext();) {
            final Date d = (Date) iter.next();
            gc.setTime(d);

            // Si les WEEK-END sont compatbilié , les ajouter au compteur
            if (weekend) {
                if (isBetween(d, beginDate, endDate)) {
                    nbph++;
                }
            } else {
                // Verifie que ce n'est pas un Samedi ou un dimance pour ompatibilité
                if ((gc.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) && (gc.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY)) {
                    if (isBetween(d, beginDate, endDate)) {
                        nbph++;
                    }
                }
            }
        }

        return nbph;
    }

    /**
     * teste si une date est entre deux autre dates
     * @param beginDate date de debut
     * @param endDate date de fin
     * @param testDate date à tester
     * @return vrai si la date testDate se situe entre la date de debut et la date de fin
     */
    public static boolean isBetween(final Date testDate, final Date beginDate, final Date endDate) {
        return (beginDate.getTime() <= testDate.getTime()) && (endDate.getTime() >= testDate.getTime());
    }
    /**
     * @return msdf_jdbc_date_heure
     */
    public static SimpleDateFormat sdf_jdbc_date_heure() {
        return msdf_jdbc_date_heure;
    }

    /**
     * @return msdf_francais
     */
    public static SimpleDateFormat sdf_francais() {
        return msdf_francais;
    }

    /**
     * @return msdf_jdbc_date
     */
    public static SimpleDateFormat sdf_jdbc_date() {
        return msdf_jdbc_date;
    }

}