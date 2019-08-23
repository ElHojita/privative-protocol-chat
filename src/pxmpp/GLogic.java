package pxmpp;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.filter.*;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.PacketCollector;
import java.util.*;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.SmackConfiguration;
import static pxmpp.Logic.varConect;
/**
 * @author Silvestre
 */
public  class GLogic {
    
    private XMPPConnection connection;
    private String room;
    private String nickname = null;
    private boolean joined = false;
    private List participants = new ArrayList();

    private PacketFilter presenceFilter;
    private PacketFilter messageFilter;
    private PacketCollector messageCollector;

    
    public   void GroupChat(XMPPConnection connection, String room) {
        this.room = room;
        messageFilter = new AndFilter(new FromContainsFilter(room),
                new PacketTypeFilter(Message.class));
        messageFilter = new AndFilter(messageFilter, new PacketFilter() {
            public boolean accept(Packet packet) {
                Message msg = (Message)packet;
                return msg.getType() == Message.Type.groupchat;
            }
        });
        messageCollector = varConect.createPacketCollector(messageFilter);
        presenceFilter = new AndFilter(new FromContainsFilter(room),
                new PacketTypeFilter(Presence.class));
        varConect.addPacketListener(new PacketListener() {
            public void processPacket(Packet packet) {
                Presence presence = (Presence)packet;
                String from = presence.getFrom();
                if (presence.getType() == Presence.Type.available) {
                    synchronized (participants) {
                        if (!participants.contains(from)) {
                            participants.add(from);
                        }
                    }
                }
                else if (presence.getType() == Presence.Type.unavailable) {
                    synchronized (participants) {
                        participants.remove(from);
                    }
                }
            }
        }, presenceFilter);
    }

    public String getRoom() {
        return room;
    }

    public  synchronized void join(String nickname) throws XMPPException {
        join(nickname, SmackConfiguration.getPacketReplyTimeout());
    }

    public synchronized void join(String nickname, long timeout) throws XMPPException {
        if (nickname == null || nickname.equals("")) {
            throw new IllegalArgumentException("Nickname must not be null or blank.");
        }

        if (joined) {
            leave();
        }

        Presence joinPresence = new Presence(Presence.Type.available);
        joinPresence.setTo(room + "/" + nickname);

        PacketFilter responseFilter = new AndFilter(
                new FromContainsFilter(room + "/" + nickname),
                new PacketTypeFilter(Presence.class));
        PacketCollector response = varConect.createPacketCollector(responseFilter);
        varConect.sendPacket(joinPresence);
        Presence presence = (Presence)response.nextResult(timeout);
        response.cancel();
        if (presence == null) {
            throw new XMPPException("No response from server.");
        }
        else if (presence.getError() != null) {
            throw new XMPPException(presence.getError());
        }
        this.nickname = nickname;
        joined = true;
    }


    public boolean isJoined() {
        return joined;
    }


    public synchronized void leave() {
        if (!joined) {
            return;
        }
        Presence leavePresence = new Presence(Presence.Type.unavailable);
        leavePresence.setTo(room + "/" + nickname);
        varConect.sendPacket(leavePresence);
        participants = new ArrayList();
        nickname = null;
        joined = false;
    }


    public String getNickname() {
        return nickname;
    }

    public int getParticipantCount() {
        synchronized (participants) {
            return participants.size();
        }
    }


    public Iterator getParticipants() {
        synchronized (participants) {
            return Collections.unmodifiableList(new ArrayList(participants)).iterator();
        }
    }


    public void addParticipantListener(PacketListener listener) {
        varConect.addPacketListener(listener, presenceFilter);
    }

    public void sendMessage(String text) throws XMPPException {
        Message message = new Message(room, Message.Type.groupchat);
        message.setBody(text);
        varConect.sendPacket(message);
    }

    public Message createMessage() {
        return new Message(room, Message.Type.groupchat);
    }


    public void sendMessage(Message message) throws XMPPException {
        varConect.sendPacket(message);
    }


    public Message pollMessage() {
        return (Message)messageCollector.pollResult();
    }

    public Message nextMessage() {
        return (Message)messageCollector.nextResult();
    }

 
    public Message nextMessage(long timeout) {
        return (Message)messageCollector.nextResult(timeout);
    }

    public void addMessageListener(PacketListener listener) {
        varConect.addPacketListener(listener, messageFilter);
    }

    public void finalize() throws Throwable {
        super.finalize();
        try {
            if (messageCollector != null) {
                messageCollector.cancel();
            }
        }
        catch (Exception e) {}
    }
    
     public static void ListenerMessage(XMPPConnection pConnection, String pUser, String pName, String pGrupo) throws Exception {
        System.out.println(String.format("Start chat with '%1$s'", pUser, pName));
        Roster roster = pConnection.getRoster();
        roster.createEntry(pUser, pName, (pGrupo == null) ? new String[] { pGrupo } : null);
    }
    
}
