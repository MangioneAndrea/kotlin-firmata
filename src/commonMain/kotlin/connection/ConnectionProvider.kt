package connection

import kotlin.jvm.Volatile
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object ConnectionProvider {
    private val connections = HashSet<Connection>()
    var activeConnection: Connection? = null
        private set

    fun getActiveConnection(): Connection? {
        return activeConnection;
    }

    fun registerConnection(connection: Connection): Boolean {
        return connections.add(connection)
    }

    fun unregisterConnection(connection: Connection): Boolean {
        return connections.remove(connection)
    }

    fun getConnections(): List<Connection> {
        return connections.toList();
    }

    fun connect(connection: Connection): Boolean {
        if (connections.contains(connection)) {
            activeConnection?.close();
            if (connection.connect()) {
                activeConnection = connection
                return true
            }
        } else {
            throw Error("Connection unknown. Please register the connection before using it")
        }
        return false;
    }
}