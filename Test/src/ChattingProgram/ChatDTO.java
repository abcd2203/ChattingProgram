package ChattingProgram;

public class ChatDTO {
	private String Roomname;
	private String Nickname;
	private String Time;
	private String msg;
	
	public String getRoomname() {
		return Roomname;
	}
	public void setRoomname(String roomname) {
		Roomname = roomname;
	}
	public String getNickname() {
		return Nickname;
	}
	public void setNickname(String nickname) {
		Nickname = nickname;
	}
	public String getTime() {
		return Time;
	}
	public void setTime(String time) {
		Time = time;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
