# Cloning and configure repository
1 Clone this repository

2 Download artifacts and extract them into `servers/integration/jenkins_data`. Be carefully!!! In `servers/integrtion/jenkins_data` should be folders like `jobs`, `users`, `plugins` etc.

3 Create a new repository on github.com

4 Change origin: `git remote set-url origin your-url`

5 Push to the github

6 Create new branch from `master`: `git checkout -b testing`

7 Push it to the origin

6 Create new branch from `testing`: `git checkout -b develop`

7 Push it to the origin



# Run servers:
1 Generate ssh keys:

- Run command ```ssh-keygen``` in directory ```~/.ssh/```

- Use   ```id_rsa_devops_group4``` as the name

- Leave passphrase empty
  
- Add to the `~/.ssh/config` file on host

```
Host devops-group4-integration-vm
        User vagrant
        Hostname 192.168.80.1  
        IdentityFile ~/.ssh/id_rsa_devops_group4

Host devops-group4-testing-vm
        User vagrant
        Hostname 192.168.80.2
        IdentityFile ~/.ssh/id_rsa_devops_group4

Host devops-group4-production-vm
        User vagrant
        Hostname 192.168.80.3
        IdentityFile ~/.ssh/id_rsa_devops_group4

```

2 Start servers:
- Go to the `/servers/integration/` 
- Comment string number 50 in `/servers/integration/Vagrantfile`
`config.vm.synced_folder "./jenkins_data", "/var/lib/jenkins", owner: "jenkins", group: "jenkins"`
- Run `vagrant up`
- After provisioning run `vagrant halt`
- Disable comment string number 50 in `/servers/integration/Vagrantfile`
  `config.vm.synced_folder "./jenkins_data", "/var/lib/jenkins", owner: "jenkins", group: "jenkins"`
- Run `vagrant up`
- Go to the `/servers/testing/` and run `vagrant up`
- Go to the `/servers/production/` and run `vagrant up`


# Configure Jenkins for right deploys 

- Connect to integration server `vagrant ssh` in directory `/servers/integration`
- Generate ssh keys ```ssh-keygen``` without passphrase
- Add to `~/.ssh/authorized_keys` in `testing` and `production` vms public key that you generate on the previous step. 
  Hint: you can copy the key in `integration vm` and using command on `production` and `testing` vm `echo "your_key" >> ~/.ssh/authorized_keys` add key
- Go in web-browser on the site `localhost:8080`
- Login, using this credentials: login - `a_nazyrov` password - `qwerty123`
- Go to `Manage Jenkins`, `Configure System`
- In the block `Publish over SSH` in the field Key change to your Private key that you generate on integration vm. Apply and save changes.





# Configure Jenkins + Github

- Connect to integration server `vagrant ssh` in directory `/servers/integration`
- Generate ssh-keys ```ssh-keygen``` without passphrase 
- Go in web-browser to the site `localhost:8080`
- Login, using this credentials login: `a_nazyrov` password: `qwerty123`
- Go to `Credentials`, then `jenkins-github`, then `update`. In the field `private key` write
your private key that you generate on integration vm. Save settings.
- In your personal github setting in `SSH and GPG keys` create new key using pub key 
that you generate on integration vm. Save settings.

- In all 3 pipelines (`CommitStage`, `pipeline_for_deploy_to_the_production_server`, `pipeline_for_deploy_to_the_production_server`) you should do this steps:
    
    - Go to {pipeline name} and click `Configure`
    - In the block `Pipeline` in the field `Repository url` change url with your ssh url for the repository. 
    - Apply and save settings
    
# Testing pipelines work

