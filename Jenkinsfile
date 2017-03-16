node {

 stage 'checkout'
  checkout scm

 stage 'lint'
  try {
   sh "./gradlew lint"
  } catch(err) {
   currentBuild.result = FAILURE
  } finally {
   androidLint canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '', unHealthy: ''
  }

 stage 'assemble'
  sh "./gradlew assemble"
     
}