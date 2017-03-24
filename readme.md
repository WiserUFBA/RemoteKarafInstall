# Instalador de bundle que estiver no repositorio maven via chamada http


dentro do diretorio execute

	mvn install

dentro do karaf 

	feature:install http http-whiteboard 
	install -s mvn:br.ufba.dcc.wiser.fot/httpInstallMvnBundle/1.0

para instalar um bundle que esteja no repositorio do maven acesse
	
	http://localhost:8181/bundleInstall?mvn=<groupId>/<artifactId>/<version>

obs: testado no karaf 4.0.4