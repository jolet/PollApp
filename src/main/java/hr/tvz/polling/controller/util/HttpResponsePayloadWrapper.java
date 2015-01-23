package hr.tvz.polling.controller.util;

/**
 * HTTP response wrapper for sending additional data inside same response to client.
 * @author joso
 *
 */
public class HttpResponsePayloadWrapper {

	private Object payload;
	private Object info;
//	Object other;
	public HttpResponsePayloadWrapper(Object info, Object payload) {
		this.payload = payload;
		this.info = info;
	}
	public Object getPayload() {
		return payload;
	}
	public void setPayload(Object payload) {
		this.payload = payload;
	}
	public Object getInfo() {
		return info;
	}
	public void setInfo(Object info) {
		this.info = info;
	}
	
	
}
