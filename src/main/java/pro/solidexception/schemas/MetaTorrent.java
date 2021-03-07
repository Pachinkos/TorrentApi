package pro.solidexception.schemas;

import pro.solidexception.models.BaseTorrent;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "torrent")
public class MetaTorrent implements BaseTorrent {
    private long id;
    private String name;
    private String category;
    private String quality;
    private String size;
    private int year;

    public MetaTorrent() {
    }

    public String getSize() {
        return size;
    }

    public int getYear() {
        return year;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
