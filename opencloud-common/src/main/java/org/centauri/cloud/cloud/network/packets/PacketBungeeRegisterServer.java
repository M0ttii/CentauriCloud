package org.centauri.cloud.cloud.network.packets;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class PacketBungeeRegisterServer implements Packet {

	@Getter private String name, host;
	@Getter private int bukkitPort;
	
	@Override
	public void encode(ByteBuf buf) {
		writeString(name, buf);
		writeString(host, buf);
		buf.writeInt(bukkitPort);
	}

	@Override
	public void decode(ByteBuf buf) {
		this.name = readString(buf);
		this.host = readString(buf);
		this.bukkitPort = buf.readInt();
	}

}