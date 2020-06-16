package com.example.taskletdemo

import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobExecutionListener
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.job.builder.FlowBuilder
import org.springframework.batch.core.job.flow.Flow
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
@EnableBatchProcessing
class JobTestConf(
    private val readTasklet: ReadTasklet,
    private val processTasklet: ProcessTasklet,
    private val writeTasklet: WriteTasklet,
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory
) {

    @Bean
    fun readStep(): Step {
        return stepBuilderFactory["readStep"]
                .tasklet(readTasklet)
                .build()
    }

    @Bean
    fun processStep(): Step {
        return stepBuilderFactory["processStep"]
                .tasklet(processTasklet)
                .build()
    }

    @Bean
    fun writeStep(): Step {
        return stepBuilderFactory["writeStep"]
                .tasklet(writeTasklet)
                .build()
    }

    @Bean
    @Throws(Exception::class)
    fun jobTest1(readStep: Step, processStep: Step, writeStep: Step): Job {
        return jobBuilderFactory["JobTest1"]
                .incrementer(RunIdIncrementer())
                .listener(listener())
                .start(readStep)
                .next(processStep)
                .next(writeStep)
                .build()
    }

    @Bean
    @Throws(Exception::class)
    fun jobTest2(readStep: Step, processStep: Step, writeStep: Step): Job {
        // readStep -> OK -> processStep
        //             NG -> writeStep
        return jobBuilderFactory["JobTest2"]
                .incrementer(RunIdIncrementer())
                .listener(listener())
                .start(readStep).on(ExitStatus.COMPLETED.exitCode).to(processStep)
                .from(readStep).on(ExitStatus.FAILED.exitCode).to(writeStep)
                .end()
                .build()
    }

    @Bean
    @Throws(Exception::class)
    fun jobTest3(readStep: Step, processStep: Step, writeStep: Step): Job {
        // readStep -> OK -> processStep
        //             NG -> writeStep
        val readProcessFlow : Flow = FlowBuilder<Flow>("readProcessFlow")
                .start(readStep).on(ExitStatus.COMPLETED.exitCode).to(processStep)
                .from(readStep).on(ExitStatus.FAILED.exitCode).fail()
                .build()

        return jobBuilderFactory["JobTest3"]
                .incrementer(RunIdIncrementer())
                .start(readProcessFlow)
                .next(writeStep)
                .end()
                .build()
    }

    @Bean
    fun listener(): JobExecutionListener {
        return JobListner()
    }
}