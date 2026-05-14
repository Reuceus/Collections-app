package city.network;

import java.nio.ByteBuffer;

public class ClientSession {
    private final ByteBuffer lengthBuffer = ByteBuffer.allocate(4);
    private ByteBuffer dataBuffer;
    private ByteBuffer responseBuffer;
    private boolean readingLength = true;

    public ByteBuffer getLengthBuffer() {
        return lengthBuffer;
    }

    public ByteBuffer getDataBuffer() {
        return dataBuffer;
    }

    public void setDataBuffer(ByteBuffer dataBuffer) {
        this.dataBuffer = dataBuffer;
    }

    public ByteBuffer getResponseBuffer() {
        return responseBuffer;
    }

    public void setResponseBuffer(ByteBuffer responseBuffer) {
        this.responseBuffer = responseBuffer;
    }

    public boolean isReadingLength() {
        return readingLength;
    }

    public void setReadingLength(boolean readingLength) {
        this.readingLength = readingLength;
    }

    public void resetForNextRequest() {
        lengthBuffer.clear();
        dataBuffer = null;
        readingLength = true;
    }
}
