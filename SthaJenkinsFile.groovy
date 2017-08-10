node {
    stage('hello'){
   echo 'Hello World'
        
    }
    stage('Preparation')
    {
        git credentialsId: '368c47b7-25fc-45e2-84ae-e03ef4fb16cc', url: 'https://github.com/gatikman/ca-project'

    }
    
   
    stage('Update and build')
    { 
     // sh 'usermod -aG docker muskanstha'
     
        sh 'docker stop codechan || true && docker rm codechan || true'
        sh 'docker login --username=muskanstha --password=muskanstha'
        
     // sh 'docker rmi muskanstha/codechan'
     // sh 'git pull origin master'
     
        sh 'docker build -t muskanstha/codechan .'
     // sh 'docker tag codechan muskanstha/codechan:latest'
     
        sh 'docker run -p 6898:5000 -d --name codechan muskanstha/codechan'
        
        
    }
    stage('Test Web Http Code')
    {
        sh 'foo=$(curl -s -o /dev/null -w "%{http_code}" http://52.57.67.76:6898)'
       // sh 'if [ $foo = '200' ]; then echo 'Pass'; else echo 'Failed' '
       
    }
    stage('Run Tests')
    {      sh 'python tests.py' }
    stage('Docker Publish')
    {
        sh 'docker push muskanstha/codechan'
    }
}
