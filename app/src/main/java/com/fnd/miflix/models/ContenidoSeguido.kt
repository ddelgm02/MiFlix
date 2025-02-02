package com.fnd.miflix.models

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "ContenidoSeguido",
    primaryKeys = ["userId", "contentId"],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ContentEntity::class,
            parentColumns = ["id"],
            childColumns = ["contentId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ContenidoSeguido(
    val userId: Int,
    val contentId: Int
)
