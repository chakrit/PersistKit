package co.omise.persister

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "records")
open class Record() {
    @PrimaryKey
    var identifier: String = ""

    var kind: String = ""

    var flags: Int = 0

    var content: ByteArray = ByteArray(0)

    constructor(identifier: String, simpleName: String, flags: Int, content: ByteArray?) : this()
}