node {
    def branch = 'master'

   stage('cleanup') {
        sh 'docker ps -q -f status=running | xargs --no-run-if-empty docker stop'
        sh 'docker ps -q -f status=running | xargs --no-run-if-empty docker rm'
    }

    stage('Redis') {
        sh("docker pull redis")
        sh("docker run --name=redis --network host  -p 6379:6379 -d redis")
    } 

    stage('Clone,buildImg,run sqldb sources') {
        /* Cloning the Repository to our Workspace */
        sh("git clone https://trivtriv:zxczxcZXC12@github.com/trivtriv/db.git")
        dir ('db') {
            /* This builds the actual image */
            docker.build("sqldb")
        }

        /* This runs the actual image */
        sh("docker run --name=sqldb --network host  -p 3306:3306 -d sqldb")
    }

    stage('Clone,buildImg,run userApi sources') {
        /* Cloning the Repository to our Workspace */
        sh("git clone https://trivtriv:zxczxcZXC12@github.com/trivtriv/userApi.git")
        dir ('userApi') {
            /* This builds the actual image */
            docker.build("userapi")
        }

        /* This runs the actual image */
        sh("docker run --name=userapi --network host  -p 8081:8081 -d userapi")
    }

    stage('Clone,buildImg,run pendingQueue sources') {
        /* Cloning the Repository to our Workspace */
        sh("git clone https://trivtriv:zxczxcZXC12@github.com/trivtriv/pendingQueue.git")
        dir ('pendingQueue') {
            /* This builds the actual image */
            docker.build("pendingqueue")
        }

        /* This runs the actual image */
        sh("docker run --name=pendingqueue --network host  -p 8082:8082 -d pendingqueue")
    }
    
    stage('Clone,buildImg,run dataHandler sources') {
        /* Cloning the Repository to our Workspace */
        sh("git clone https://trivtriv:zxczxcZXC12@github.com/trivtriv/dataHandler.git")
        dir ('dataHandler') {
            /* This builds the actual image */
            docker.build("datahandler")
        }

        /* This runs the actual image */
        sh("docker run --name=datahandler --network host  -p 8083:8083 -d datahandler")
    } 

    stage('cleanup') {
         deleteDir()
    }
}