package Helper;

/**
 * Created by gavri on 18.07.2017.
 */
public enum Test {
    НЕТ_ДАННЫХ("Нет данных"),
    С_АНГЛИЙСКОГО_НА_РУССИКЙ("С английского на русский"),
    С_РУССКОГО_НА_АНГЛИЙСКИЙ("С русского на английский");

    private String nameTest;

    Test(String nameTest) {this.nameTest=nameTest;}

    public String getNameTest() {
        return nameTest;
    }

    @Override
    public String toString() {
        return nameTest;
    }
}
