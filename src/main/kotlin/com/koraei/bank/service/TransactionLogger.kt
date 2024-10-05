package com.koraei.bank.service


import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.io.File
import java.io.FileWriter

@Component
class TransactionLogger : TransactionObserver {
    private val logger = LoggerFactory.getLogger(TransactionLogger::class.java)
    private val logFile = File("transactions.log")

    init {
        if (!logFile.exists()) {
            logFile.createNewFile()
        }
    }

    override fun onTransaction(accountId: String, transactionType: String, amount: Double) {
        val logMessage = "Account: $accountId | Type: $transactionType | Amount: $amount"
        logger.info(logMessage)
        FileWriter(logFile, true).use {
            it.write("$logMessage\n")
        }
    }
}
