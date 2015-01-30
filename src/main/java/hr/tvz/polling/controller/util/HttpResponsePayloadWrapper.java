package hr.tvz.polling.controller.util;

/**
 * HTTP response wrapper for sending additional data inside same response to
 * client.
 * 
 * @author joso
 *
 */
public class HttpResponsePayloadWrapper {

	private Object payload;
	private Object info;
	private Object additionalIntel;

	public HttpResponsePayloadWrapper(Object info, Object payload) {
		this.payload = payload;
		this.info = info;
	}

	public HttpResponsePayloadWrapper(Object info, Object payload, Object additionalIntel) {
		this.payload = payload;
		this.info = info;
		this.additionalIntel = additionalIntel;
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
	
	public Object getAdditionalIntel() {
		return additionalIntel;
	}

	public void setAdditionalIntel(Object additionalIntel) {
		this.additionalIntel = additionalIntel;
	}

}
