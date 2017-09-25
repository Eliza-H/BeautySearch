package elizah.springbootandroid;

import java.util.List;

/**
 * Created by elh on 18.09.17.
 */

class ItemObjects {
    public ItemObjects(String alkane, int ic_delete, String description, boolean isfav, int isturned) {
        this.alkane = alkane;
        this.ic_delete = ic_delete;
        this.description = description;
        this.isfav = isfav;
        this.isturned = isturned;
    }


    String alkane;
    int ic_delete;
    String description;
    boolean isfav;
    int isturned;
    Long id;
    List<ItemObjects> gaggeredList;

    public List<ItemObjects> getGaggeredList() {
        return gaggeredList;
    }

    public void setGaggeredList(List<ItemObjects> gaggeredList) {
        this.gaggeredList = gaggeredList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getIsturned() {
        return isturned;
    }

    public void setIsturned(int isturned) {
        this.isturned = isturned;
    }

    public boolean getIsfav() {
        return isfav;
    }

    public void setIsfav(boolean isfav) {
        this.isfav = isfav;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return alkane;
    }

    public void setName(String alkane) {
        this.alkane = alkane;
    }

    public int getPhoto() {
        return ic_delete;
    }

    public void setPhoto(int ic_delete) {
        this.ic_delete = ic_delete;
    }

}
