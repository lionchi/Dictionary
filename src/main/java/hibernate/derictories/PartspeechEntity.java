package hibernate.derictories;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;

/**
 * Created by gavri on 14.07.2017.
 */
@Entity
@Table(name = "partspeech", schema = "dictionary")
public class PartspeechEntity {
    private int idpartspeech;
    private String namePart;

    private IntegerProperty idPartSpeechP = new SimpleIntegerProperty();
    private StringProperty namePartP = new SimpleStringProperty();

    @Transient
    public String getNamePartP(){return  namePartP.get();}

    public StringProperty namePartPProperty(){return namePartP;}

    public void setNamePartP(String namePartP){this.namePartP.set(namePartP);}

    @Transient
    public int getIdPartSpeechP() {
        return idPartSpeechP.get();
    }

    public IntegerProperty idPartSpeechPProperty() {
        return idPartSpeechP;
    }

    public void setIdPartSpeechP(int idCategoryP) {
        this.idPartSpeechP.set(idCategoryP);
    }

    @Id
    @Column(name = "idpartspeech", nullable = false)
    public int getIdpartspeech() {
        return idpartspeech;
    }

    public void setIdpartspeech(int idpartspeech) {
        this.idpartspeech = idpartspeech;
        setIdPartSpeechP(idpartspeech);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PartspeechEntity that = (PartspeechEntity) o;

        if (idpartspeech != that.idpartspeech) return false;
        if (namePart != null ? !namePart.equals(that.namePart) : that.namePart != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idpartspeech;
        result = 31 * result + (namePart != null ? namePart.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return namePart;
    }
}
