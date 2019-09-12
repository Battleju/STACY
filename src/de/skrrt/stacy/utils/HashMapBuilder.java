package de.skrrt.stacy.utils;

public class HashMapBuilder {

    public static String extractPrice(String text){
        int separatingPoint = 0;
        char[] cText = text.toCharArray();
        for(int i = 0; i < cText.length; i++){
            if(cText[i] == ','){
                cText[i] = ' ';
                break;
            }
            cText[i] = ' ';
        }


        String textNew = String.valueOf(cText);
        cText = textNew.trim().toCharArray();

        for(int i = 0; i < cText.length; i++){
            if(cText[i] == ':'){
                cText[i] = ' ';
                break;
            }
            cText[i] = ' ';
        }

        textNew = String.valueOf(cText);
        cText = textNew.trim().toCharArray();

        for(int i = 0; i < cText.length; i++){
            if(cText[i] == ':'){
                cText[i] = ' ';
                break;
            }
            cText[i] = ' ';
        }

        textNew = String.valueOf(cText);
        cText = textNew.trim().toCharArray();

        for(int i = 0; i < cText.length; i++){
            if(cText[i] == 'c'){
                cText[i] = ' ';
                break;
            }
            cText[i] = ' ';
        }

        textNew = String.valueOf(cText);
        cText = textNew.trim().toCharArray();

        for(int i = 0; i < cText.length; i++){
            if(cText[i] == '"'){
                cText[i] = ' ';
                break;
            }
            cText[i] = ' ';
        }

        textNew = String.valueOf(cText);
        cText = textNew.trim().toCharArray();

        for(int i = 0; i < cText.length; i++){
            if(cText[i] == '"'){
                cText[i] = ' ';
                break;
            }
            cText[i] = ' ';
        }

        textNew = String.valueOf(cText);
        cText = textNew.trim().toCharArray();

        String finalText;
        String[] parts = textNew.split("\"");
        finalText = parts[0];

        return finalText.trim();
    }

    public static void main(String[] skrrt){
        extractPrice("{\"error\":[],\"result\":{\"XXBTZUSD\":{\"a\":[\"11889.20000\",\"1\",\"1.000\"],\"b\":[\"11889.10000\",\"1\",\"1.000\"],\"c\":[\"11889.20000\",\"0.04000000\"],\"v\":[\"9502.41457413\",\"25279.30913565\"],\"p\":[\"12368.69580\",\"12694.66592\"],\"t\":[35743,98873],\"l\":[\"11253.60000\",\"11253.60000\"],\"h\":[\"13360.90000\",\"13875.70000\"],\"o\":\"12933.70000\"}}}");
    }
}
