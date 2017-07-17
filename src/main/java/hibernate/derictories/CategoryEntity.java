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
@Table(name = "category", schema = "dictionary")
public class CategoryEntity implements IModel {
    private int idcategory;
    private String nameCategory;

    private IntegerProperty idCategoryP = new SimpleIntegerProperty();
    private StringProperty nameCategoryP = new SimpleStringProperty();

    @Transient
    public String getNameCategoryP(){return  nameCategoryP.get();}

    public StringProperty nameCategoryPProperty(){return nameCategoryP;}

    public void setNameCategoryP(String nameCategoryP){this.nameCategoryP.set(nameCategoryP);}

    @Transient
    public int getIdCategoryP() {
        return idCategoryP.get();
    }

    public IntegerProperty idCategoryPProperty() {
        return idCategoryP;
    }

    public void setIdCategoryP(int idCategoryP) {
        this.idCategoryP.set(idCategoryP);
    }

    @Id
    @Column(name = "idcategory", nullable = false)
    public int getIdcategory() {
        return idcategory;
    }

    public void setIdcategory(int idcategory) {
        this.idcategory = idcategory;
        setIdCategoryP(idcategory);
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

        CategoryEntity that = (CategoryEntity) o;

        if (idcategory != that.idcategory) return false;
        if (nameCategory != null ? !nameCategory.equals(that.nameCategory) : that.nameCategory != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idcategory;
        result = 31 * result + (nameCategory != null ? nameCategory.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return nameCategory;
    }
}
