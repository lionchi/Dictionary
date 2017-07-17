package hibernate.derictories;

import hibernate.IModel;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;

/**
 * Created by gavri on 14.07.2017.
 */
@Entity
@Table(name = "maintable", schema = "dictionary")
public class MaintableEntity implements IModel {
    private int idmaintable;
    private String word;
    private String transfer;
    private int partspeech_idpartspeech;
    private int category_idcategory;

    private IntegerProperty idMainTableP = new SimpleIntegerProperty();
    private StringProperty wordP = new SimpleStringProperty();
    private StringProperty transferP = new SimpleStringProperty();
    private IntegerProperty partspeech_idpartspeechP = new SimpleIntegerProperty();
    private IntegerProperty category_idcategoryP = new SimpleIntegerProperty();

    @Transient
    public int getIdMainTableP() {
        return idMainTableP.get();
    }

    public IntegerProperty idMainTablePProperty() {
        return idMainTableP;
    }

    public void setIdMainTablePP(int idCategoryP) {
        this.idMainTableP.set(idCategoryP);
    }

    @Transient
    public String getWordP(){return  wordP.get();}

    public StringProperty wordPProperty(){return wordP;}

    public void setWordP(String wordP){this.wordP.set(wordP);}

    @Transient
    public  String getTrasferP(){return  transferP.get();}

    public StringProperty transferPProperty(){return  transferP;}

    public void setTransferP (String transferP){this.transferP.set(transferP);}

    @Transient
    public int getPartspeech_idpartspeechP() {return partspeech_idpartspeechP.get();}

    public IntegerProperty Partspeech_idpartspeechPProperty(){return partspeech_idpartspeechP;}

    public void setPartspeech_idpartspeechP(int partspeech_idpartspeechP){this.partspeech_idpartspeechP.set(partspeech_idpartspeechP);}


    @Transient
    public int getCategory_idcategoryP(){return category_idcategoryP.get();}

    public IntegerProperty Category_idcategoryPProperty(){return category_idcategoryP;}

    public void setCategory_idcategoryP(int category_idcategoryP){this.category_idcategoryP.set(category_idcategoryP);}

    @Id
    @Column(name = "idmaintable", nullable = false)
    public int getIdmaintable() {
        return idmaintable;
    }

    public void setIdmaintable(int idmaintable) {
        this.idmaintable = idmaintable;
        setIdMainTablePP(idmaintable);
    }

    @Basic
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
    @Column(name = "partspeech_idpartspeech", nullable = false)
    public int getPartspeech_idpartspeech() {
        return partspeech_idpartspeech;
    }

    public void setPartspeech_idpartspeech(int partspeech_idpartspeech) {
        this.partspeech_idpartspeech = partspeech_idpartspeech;
        setPartspeech_idpartspeechP(partspeech_idpartspeech);
    }

    @Basic
    @Column(name = "category_idcategory", nullable = false)
    public int getCategory_idcategory() {
        return category_idcategory;
    }

    public void setCategory_idcategory(int category_idcategory) {
        this.category_idcategory = category_idcategory;
        setCategory_idcategoryP(category_idcategory);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MaintableEntity that = (MaintableEntity) o;

        if (idmaintable != that.idmaintable) return false;
        if (word != null ? !word.equals(that.word) : that.word != null) return false;
        if (transfer != null ? !transfer.equals(that.transfer) : that.transfer != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idmaintable;
        result = 31 * result + (word != null ? word.hashCode() : 0);
        result = 31 * result + (transfer != null ? transfer.hashCode() : 0);
        return result;
    }
}
