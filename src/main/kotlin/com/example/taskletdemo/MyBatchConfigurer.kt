package com.example.taskletdemo

import org.springframework.batch.core.configuration.annotation.BatchConfigurer
import org.springframework.batch.core.explore.JobExplorer
import org.springframework.batch.core.explore.support.MapJobExplorerFactoryBean
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.launch.support.SimpleJobLauncher
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean
import org.springframework.batch.support.transaction.ResourcelessTransactionManager
import org.springframework.stereotype.Component
import org.springframework.transaction.PlatformTransactionManager

@Component
class MyBatchConfigurer: BatchConfigurer {
    private val transactionManager = ResourcelessTransactionManager()
    private val mapJobRepositoryFactoryBean = MapJobRepositoryFactoryBean(transactionManager).also { it.afterPropertiesSet() }
    private val jobRepository = mapJobRepositoryFactoryBean.`object`
    private val jobExplorer = MapJobExplorerFactoryBean(mapJobRepositoryFactoryBean).also { it.afterPropertiesSet() }.`object`
    private val jobLauncher = SimpleJobLauncher().also {
        it.setJobRepository(jobRepository)
        it.afterPropertiesSet()
    }

    override fun getJobRepository(): JobRepository = jobRepository
    override fun getJobLauncher(): JobLauncher = jobLauncher
    override fun getJobExplorer(): JobExplorer = jobExplorer
    override fun getTransactionManager(): PlatformTransactionManager = transactionManager
}