package controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by Demyd on 30.09.2016.
 */
public class Analizer {

    public static Combination analis(Collection<Card> cards1, Collection<Card> cards2) {
        ArrayList<Card> list = new ArrayList<>();
        for (Card card : cards1) {
            list.add(card);
        }
        for (Card card : cards2) {
            list.add(card);
        }
        Collections.sort(list);

     /*   описание комбинаций:
        0 - нет комбинаций(старшая карта)
        1 - пары
        2 - две пары
        3 - сет
        4 - стрит
        5- флеш
        6 - фулл хаус
        7 - каре
        8 - стритфлеш
        9 - флеш рояль
        */

        int combination = 0;

        //поиск пары
        ArrayList<Card> firstPair = new ArrayList<>();
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).value == list.get(i + 1).value) {
                combination = 1;
                firstPair.clear();
                firstPair.add(list.get(i));
                firstPair.add(list.get(i + 1));
            }
        }

        //поиск второй пары
        ArrayList<Card> secondPair = new ArrayList<>();//хранилище для второй пары
        if (combination == 1) {
            ArrayList<Card> list2 = (ArrayList<Card>) list.clone();
            //удаление первой пары из оригинального перечня
            list2.remove(firstPair.get(0));
            list2.remove(firstPair.get(1));
            //поиск второй пары в стеке
            for (int i = 0; i < list2.size() - 1; i++) {
                if (list2.get(i).value == list2.get(i + 1).value) {
                    combination = 2;
                    secondPair.clear();
                    secondPair.add(list.get(i));
                    secondPair.add(list.get(i + 1));
                }
            }

        }

        //поиск сета
        ArrayList<Card> fullHouse = null;
        ArrayList<Card> setCombination = new ArrayList<>();
        for (int i = 0; i < list.size() - 2; i++) {
            if (list.get(i).value == list.get(i + 1).value && list.get(i).value == list.get(i + 2).value) {
                combination = 3;
                setCombination.clear();
                setCombination.add(list.get(i));
                setCombination.add(list.get(i + 1));
                setCombination.add(list.get(i + 2));
            }
        }

        //Поиск фулл Хауса
        if (combination == 3) {
            fullHouse = new ArrayList<>();
            ArrayList<Card> list3 = (ArrayList<Card>) list.clone();
            //удаление сета из оригинального перечня
            list3.remove(setCombination.get(0));
            list3.remove(setCombination.get(1));
            list3.remove(setCombination.get(2));
            //поиск пары в перечне
            for (int i = 0; i < list3.size() - 1; i++) {
                if (list3.get(i).value == list3.get(i + 1).value) {
                    combination = 6;
                    fullHouse.clear();
                    fullHouse.add(list3.get(i));
                    fullHouse.add(list3.get(i + 1));
                }
            }
            if (combination == 6) {
                fullHouse.add(setCombination.get(0));
                fullHouse.add(setCombination.get(1));
                fullHouse.add(setCombination.get(2));
            }
        }

        // поиск стрита
        ArrayList<Card> streetCombination = null;
        if (list.size() >= 5) {
            streetCombination = new ArrayList<>();
            ArrayList<Card> list3 = (ArrayList<Card>) list.clone();
            //удаление одинаковых по value из оригинального перечня
            for (int i = 0; i < list3.size() - 1; i++) {
                if (list3.get(i).sameValue(list3.get(i + 1)))//сравнение двух соседних карт
                    list3.remove(list3.get(i + 1));
                if (!(i < list3.size() - 1))
                    break;
            }
            if (list3.size() >= 5) {
                for (int i = 0; i < list3.size() - 4; i++) {
                    if (list3.get(i).value == list3.get(i + 1).value - 1 &&
                            list3.get(i + 1).value == list3.get(i + 2).value - 1 &&
                            list3.get(i + 2).value == list3.get(i + 3).value - 1 &&
                            list3.get(i + 3).value == list3.get(i + 4).value - 1) {
                        combination = combination < 4 ? 4 : combination;
                        streetCombination.clear();
                        streetCombination.add(list3.get(i));
                        streetCombination.add(list3.get(i + 1));
                        streetCombination.add(list3.get(i + 2));
                        streetCombination.add(list3.get(i + 3));
                        streetCombination.add(list3.get(i + 4));
                    }
                }
            }
        }

        //поиск флеша
        ArrayList<Card> streetFlash = null;
        ArrayList<Card> flashlist = null;
        int[] masks = new int[4];

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getMask() == Mask.CLUB) {
                masks[0]++;
            }
            if (list.get(i).getMask() == Mask.DIAMOND) {
                masks[1]++;
            }
            if (list.get(i).getMask() == Mask.HEART) {
                masks[2]++;
            }
            if (list.get(i).getMask() == Mask.SPADE) {
                masks[3]++;
            }
        }

        int flashMask = 0;
        for (int i = 0; i < 4; i++) {
            if (masks[i] >= 5) {
                combination = combination < 5 ? 5 : combination;
                flashMask = i;

            }
        }
        if (combination == 5) {
            flashlist = new ArrayList<>();
            Mask mask = null;
            switch (flashMask) {
                case 0:
                    mask = Mask.CLUB;
                    break;
                case 1:
                    mask = Mask.DIAMOND;
                    break;
                case 2:
                    mask = Mask.HEART;
                    break;
                case 3:
                    mask = Mask.SPADE;
            }

            int i = list.size() - 1;

            ////////////////////

            while (i >= 0) {
                if (list.get(i).getMask() == mask) {
                    flashlist.add(list.get(i));
//////////////////////////////
                }
                i--;
                if (flashlist.size()==5)break;
            }
            //поиск стрит флеша
            Collections.sort(flashlist);
            if (isSet(flashlist).size() > 0) {
                streetFlash = isSet(flashlist);
                combination = 8;
                if (streetFlash.get(streetFlash.size() - 1).value == 14)
                    combination = 9;
            }

        }

        // поиск каре
        ArrayList<Card> kareCombination = null;
        if (list.size() >= 4)
            kareCombination = new ArrayList<>();
        {
            for (int i = 0; i < list.size() - 3; i++) {
                if (list.get(i).value == list.get(i + 1).value &&
                        list.get(i).value == list.get(i + 2).value &&
                        list.get(i).value == list.get(i + 3).value) {
                    combination = 7;
                    kareCombination.clear();
                    kareCombination.add(list.get(i));
                    kareCombination.add(list.get(i + 1));
                    kareCombination.add(list.get(i + 2));
                    kareCombination.add(list.get(i + 3));
                }
            }
        }
        ArrayList<Card> finalList = null;
        switch (combination) {
            //вывод только кикера(1 карта)
            case 0:
                ArrayList<Card> lis = new ArrayList<>();
                lis.add(list.get(list.size() - 1));
                finalList = lis;
                break;
            //вывод одной пары(2 карты)
            case 1:
                finalList = firstPair;
                break;
            //вывод двух пар(4 карты)
            case 2:
                finalList = new ArrayList<>();
                for (Card card : firstPair)
                    finalList.add(card);
                for (Card card : secondPair)
                    finalList.add(card);
                break;
            case 3:
                finalList = setCombination;
                break;
            case 4:
                finalList = streetCombination;
                break;
            case 5:
                finalList = flashlist;
                break;
            case 6:
                finalList = fullHouse;
                break;
            case 7:
                finalList = kareCombination;
                break;
            case 8:
                finalList = streetFlash;
                break;
            case 9:
                finalList = streetFlash;
                break;
        }
        return new Combination(finalList, combination);


    }

    // нужно отправлять на проверку только перечень без дубликатов value/ если не является стритом - возвращает пустой лист.
    public static ArrayList<Card> isSet(ArrayList<Card> list) {

        ArrayList<Card> streetCombination = new ArrayList<>();
        if (list.size() >= 5) {
            for (int i = 0; i < list.size() - 4; i++) {
                if (list.get(i).value == list.get(i + 1).value - 1 &&
                        list.get(i + 1).value == list.get(i + 2).value - 1 &&
                        list.get(i + 2).value == list.get(i + 3).value - 1 &&
                        list.get(i + 3).value == list.get(i + 4).value - 1) {

                    streetCombination.clear();
                    streetCombination.add(list.get(i));
                    streetCombination.add(list.get(i + 1));
                    streetCombination.add(list.get(i + 2));
                    streetCombination.add(list.get(i + 3));
                    streetCombination.add(list.get(i + 4));

                }
            }
        }
        return streetCombination;
    }


}
