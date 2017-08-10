node {
  stage('Preparation') 
  {
    git credentialsId: '223401e7-ef79-4ca4-9e74-628387fd52d2', url: 'https://github.com/gatikman/ca-project'
  }
  stage('Test')
  {
     sh 'echo "Build"'
     //sh 'if (curl -s -o /dev/null -w "%{http_code}" http://52.59.6.52:6898'){ == 200}else{echo 'error'}
  } 
  stage('Build')
  {
     sh 'docker build -t code-chan .'
     
  }
  stage('Deploy')
  {
     sh 'docker images'
     sh 'docker ps -a'
     //sh '[ "$(docker ps -a | grep code-chan)" ] && docker rm -f code-chan'
     sh 'docker stop code-chan || true && docker rm code-chan || true'
     sh 'docker run -p 6898:5000 -d --name code-chan code-chan'
     //sh 'docker rm $(docker stop $(docker ps -a -q --filter ancestor=code-chan --format="{{.ID}}"))'
     //sh '[ ! "$(docker ps -a | grep code-chan)" ] && docker run -p 6898:5000 -d code-chan'
     //sh 'docker build -t code-chan .'
     //sh 'docker run -p 6898:5000 -d code-chan'
  }
}
