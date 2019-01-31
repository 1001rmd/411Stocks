package stockdata;

import java.util.ArrayList;

/**
 *
 * @author Anant Narayan Gaur
 */
public class Company {

    private String symbol;
    private String companyName;
    private String exchange;
    private String industry;
    private String website;
    private String description;
    private String CEO;
    private String issueType;
    private String sector;
    ArrayList<String> Tags;

    Company() {
    }

    public Company(String symbol, String Name, String exchange, String field, String website, String description, String CEO, String issueType, String sector, ArrayList<String> Tags) {
        this.symbol = symbol;
        this.companyName = Name;
        this.exchange = exchange;
        this.industry = field;
        this.website = website;
        this.description = description;
        this.CEO = CEO;
        this.issueType = issueType;
        this.sector = sector;
        this.Tags = Tags;
    }

    @Override
    public String toString() {
        return "Company{" + "symbol=" + symbol + ", Name=" + companyName + ", exchange=" + exchange + ", field=" + industry + ", website=" + website + ", description=" + description + ", CEO=" + CEO + ", issueType=" + issueType + ", sector=" + sector + ", Tags=" + Tags + '}';
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String Name) {
        this.companyName = Name;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getindustry() {
        return industry;
    }

    public void setindustry(String field) {
        this.industry = field;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCEO() {
        return CEO;
    }

    public void setCEO(String CEO) {
        this.CEO = CEO;
    }

    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public ArrayList<String> getTags() {
        return Tags;
    }

    public void setTags(ArrayList<String> Tags) {
        this.Tags = Tags;
    }

}
