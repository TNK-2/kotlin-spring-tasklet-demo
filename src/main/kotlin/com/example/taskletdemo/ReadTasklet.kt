package com.example.taskletdemo

import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.StepExecution
import org.springframework.batch.core.StepExecutionListener
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Component

@Component
class ReadTasklet: Tasklet, StepExecutionListener {

    private val list = ArrayList<String>()

    override fun beforeStep(stepExecution: StepExecution) {}

    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus {
        println("ReadTasklet!!")
        this.list.add("test1")
        this.list.add("test2")
        this.list.add("test3")
        return RepeatStatus.FINISHED
    }

    override fun afterStep(stepExecution: StepExecution): ExitStatus? {
        stepExecution
            .jobExecution
            .executionContext
            .put("result", this.list);
        return ExitStatus.COMPLETED;
    }
}