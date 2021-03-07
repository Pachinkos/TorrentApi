package pro.solidexception.models;

public interface BaseTorrent {

    long getId();
    String getName();
    String getQuality();
    String getCategory();
    String getSize();
    int getYear();
}
