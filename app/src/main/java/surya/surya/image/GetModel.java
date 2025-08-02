package surya.surya.image;

public class GetModel {
    private String ModelID;
    private String ModelImageURL;
    private String ModelNo;
    private String Price;

    public String getModelImageURL() {
        return this.ModelImageURL;
    }

    public void setModelImageURL(String modelImageURL) {
        this.ModelImageURL = modelImageURL;
    }

    public String getModelNo() {
        return this.ModelNo;
    }

    public void setModelNo(String modelNo) {
        this.ModelNo = modelNo;
    }

    public String getModelID() {
        return this.ModelID;
    }

    public void setModelID(String modelID) {
        this.ModelID = modelID;
    }

    public String getPrice() {
        return this.Price;
    }

    public void setPrice(String price) {
        this.Price = price;
    }
}
