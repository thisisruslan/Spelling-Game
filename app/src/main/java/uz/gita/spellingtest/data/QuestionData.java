package uz.gita.spellingtest.data;


import androidx.annotation.Keep;

import uz.gita.spellingtest.R;

@Keep
public class QuestionData {

    private int id;
    private Boolean isValid;
    private String question;
    private String variant1;
    private String variant2;
    private String variant3;
    private String variant4;
    private String answer;

    public QuestionData() {

    }

    public QuestionData(int id,boolean isValid, String question, String variant1, String variant2, String variant3, String variant4, String answer) {
        this.id = id;
        this.isValid = isValid;
        this.question = question;
        this.variant1 = variant1;
        this.variant2 = variant2;
        this.variant3 = variant3;
        this.variant4 = variant4;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getVariantById(int index) {
        if (index == 0 || index == R.id.variant1) return variant1;
        if (index == 1 || index == R.id.variant2) return variant2;
        if (index == 2 || index == R.id.variant3) return variant3;
        if (index == 3 || index == R.id.variant4) return variant4;
        throw new IllegalArgumentException("Qáte argument berildi");
    }

    public String getAnswer() {
        return answer;
    }

    public boolean getIsValid() { return isValid; }

    public String getVariant1() {
        return variant1;
    }

    public String getVariant2() {
        return variant2;
    }

    public String getVariant3() {
        return variant3;
    }

    public String getVariant4() {
        return variant4;
    }

    public int getId() {
        return id;
    }

    public void setVariant1(String variant1) {
        this.variant1 = variant1;
    }

    public void setVariant2(String variant2) {
        this.variant2 = variant2;
    }

    public void setVariant3(String variant3) {
        this.variant3 = variant3;
    }

    public void setVariant4(String variant4) {
        this.variant4 = variant4;
    }
}
