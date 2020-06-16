package com.example.taskletdemo

import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.StepExecution
import org.springframework.batch.core.StepExecutionListener
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.item.ExecutionContext
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Component

@Component
class ProcessTasklet: Tasklet, StepExecutionListener {

    private lateinit var list: ArrayList<String>

    override fun beforeStep(stepExecution: StepExecution) {
        this.list = stepExecution
                .jobExecution
                .executionContext
                .get("result") as ArrayList<String>
    }

    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus {
        println("ProcessTasklet!!")
        this.list.forEach{s -> print(s)}
        return RepeatStatus.FINISHED
    }

    override fun afterStep(stepExecution: StepExecution): ExitStatus? {
        return ExitStatus.COMPLETED;
    }
}