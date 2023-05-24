package com.example.appthitracnghiem_001.question;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class QuestionController {
    private DBHelper dbHelper;

    public QuestionController(Context context) {
        dbHelper= new DBHelper(context);
    }

    //Lấy danh sách câu hỏi
    public ArrayList<Question> getQuestion(int num_exam, String subject){
        ArrayList<Question> lsData= new ArrayList<Question>();
        SQLiteDatabase db= dbHelper.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM tracnghiem WHERE num_exam = '"+num_exam+"' AND subject='"+subject+"' ORDER BY random() LIMIT 20",null);
        cursor.moveToFirst();
        do {
            Question item;
            item= new Question(cursor.getInt(0), cursor.getString(1),cursor.getString(2),cursor.getString(3),
                    cursor.getString(4),cursor.getString(5),cursor.getString(6),
                    cursor.getInt(7),cursor.getString(8),cursor.getString(9),"");
            lsData.add(item);
        }while (cursor.moveToNext());
        return lsData;
     }

    public ArrayList<Question> mixQuestions(ArrayList<Question> list) {
        Integer resultOld = 0;
        Question questionNew;
        for (int i = 0; i < list.size(); i++) {
            List<String> listAnswer = new ArrayList<>();
            listAnswer.add(list.get(i).getAns_a());
            listAnswer.add(list.get(i).getAns_b());
            listAnswer.add(list.get(i).getAns_c());
            listAnswer.add(list.get(i).getAns_d());
            questionNew = new Question();
            resultOld = mapResult.get(list.get(i).getResult());
            List<Integer> positionRandom = getListPositionRandom(0, 3);
            //return  2 3 1 4
            if (positionRandom.get(0) == resultOld) {
                questionNew.setResult("A");
            } else if (positionRandom.get(1) == resultOld) {
                questionNew.setResult("B");
            }else if (positionRandom.get(2) == resultOld) {
                questionNew.setResult("C");
            }else if (positionRandom.get(3) == resultOld) {
                questionNew.setResult("D");
            }
            questionNew.setAns_a(listAnswer.get(positionRandom.get(0)));
            questionNew.setAns_b(listAnswer.get(positionRandom.get(1)));
            questionNew.setAns_c(listAnswer.get(positionRandom.get(2)));
            questionNew.setAns_d(listAnswer.get(positionRandom.get(3)));

            list.get(i).setAns_a(questionNew.getAns_a());
            list.get(i).setAns_b(questionNew.getAns_b());
            list.get(i).setAns_c(questionNew.getAns_c());
            list.get(i).setAns_d(questionNew.getAns_d());
            list.get(i).setResult(questionNew.getResult());
        }
        return list;
    }

     Map<String, Integer> mapResult = new HashMap<String, Integer>(){{
         put("A",0);
         put("B",1);
         put("C",2);
         put("D",3);
     }}  ;

    public List<Integer> getListPositionRandom(int max, int min){
        int resultRandom = 0;
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            do {
                resultRandom = randInt(max, min);
                if (list.contains(resultRandom)){
                }else {
                    list.add(resultRandom);
                    break;
                }
            } while (true);
        }
        return list;
    }
    public static int randInt(int min, int max) {

        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }


}
