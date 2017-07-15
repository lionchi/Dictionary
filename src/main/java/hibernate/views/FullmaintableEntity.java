package hibernate.views;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;


/**
 * Created by gavri on 15.07.2017.
 */
@Entity
@Table(name = "fullmaintable", schema = "dictionary")
public class FullmaintableEntity {
    private String word;
    private String transfer;
    private String namePart;
    private String nameCategory;

    private StringProperty wordP = new SimpleStringProperty();
    private StringProperty transferP = new SimpleStringProperty();
    private StringProperty namePartP = new SimpleStringProperty();
    private StringProperty nameCategoryP = new SimpleStringProperty();

    @Id
    @Column(name = "word", nullable = false, length = -1)
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
        setWordP(word);
    }

    @Basic
    @Column(name = "transfer", nullable = false, length = -1)
    public String getTransfer() {
        return transfer;
    }

    public void setTransfer(String transfer) {
        this.transfer = transfer;
        setTransferP(transfer);
    }

    @Basic
    @Column(name = "name_part", nullable = false, length = -1)
    public String getNamePart() {
        return namePart;
    }

    public void setNamePart(String namePart) {
        this.namePart = namePart;
        setNamePartP(namePart);
    }

    @Basic
    @Column(name = "name_category", nullable = false, length = -1)
    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
        setNameCategoryP(nameCategory);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FullmaintableEntity that = (FullmaintableEntity) o;

        if (word != null ? !word.equals(that.word) : that.word != null) return false;
        if (transfer != null ? !transfer.equals(that.transfer) : that.transfer != null) return false;
        if (namePart != null ? !namePart.equals(that.namePart) : that.namePart != null) return false;
        if (nameCategory != null ? !nameCategory.equals(that.nameCategory) : that.nameCategory != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = word != null ? word.hashCode() : 0;
        result = 31 * result + (transfer != null ? transfer.hashCode() : 0);
        result = 31 * result + (namePart != null ? namePart.hashCode() : 0);
        result = 31 * result + (nameCategory != null ? nameCategory.hashCode() : 0);
        return result;
    }

    @Transient
    public String getWordP(){return wordP.get();}

    public StringProperty WordPProperty (){return wordP;}

    public void setWordP (String wordP){this.wordP.set(wordP);}

    @Transient
    public String getTransferP(){return transferP.get();}

    public StringProperty TransferPProperty(){return transferP;}

    public void setTransferP (String transferP){this.transferP.set(transferP);}

    @Transient
    public String getNamePartP(){return namePartP.get();}

    public StringProperty NamePartPProperty(){return  namePartP;}

    public void setNamePartP(String namePartP){this.namePartP.set(namePartP);}

    @Transient
    public String getNameCategoryP(){return nameCategoryP.get();}

    public StringProperty NameCategoryPProperty(){return nameCategoryP;}

    public void setNameCategoryP(String nameCategoryP){this.nameCategoryP.set(nameCategoryP);}
}
