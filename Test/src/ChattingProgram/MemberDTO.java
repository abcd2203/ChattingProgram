package ChattingProgram;
public class MemberDTO {
    
    
    private String id;
    private String pwd;
    private String name;
    private String tel;
    private String addr1;
    private String addr2;
    private String post;
    private String chatname;
    private String gender;
    public String profilepath;

 
    //이클립스팁 : Getter/Setter 만들기
    //             우클릭 -> source->Generate Getters And Setters-> [Select AlL] -> [OK]
    
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPwd() {
        return pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
    public String getAddr1() {
        return addr1;
    }
    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }
    public String getAddr2() {
        return addr2;
    }
    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }
    public String getPost() {
        return post;
    }
    public void setPost(String post) {
        this.post = post;
    }
    public String getChatname() {
        return chatname;
    }
    public void setChatname(String chatname) {
        this.chatname = chatname;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getProfilePath() {
        return profilepath;
    }
    public void setProfilePath(String profilepath) {
        this.profilepath = profilepath;
    }

    
    //DTO 객체 확인
    //이클립스팁 : toString() 자동생성: 우클릭 -> source -> Generate toString->[OK]
    @Override
    public String toString() {
        return "MemberDTO [id=" + id + ", pwd=" + pwd + ", name=" + name
                + ", tel=" + tel + ", addr1=" + addr1 + " , post=" + post
                + ", chatname=" + chatname + ", gender=" + gender + ", profilepath=" + profilepath
                + ", addr2=" + addr2 + "]";
    }
}