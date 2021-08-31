function() {
  karate.configure('connectTimeout', 5000);
  karate.configure('readTimeout', 5000);

  var config = { };

  if (!karate.env) {
    env = 'test'; // a custom 'intelligent' default
  }

  switch(env){
	case 'test':
		config.baseUrl = 'http://localhost:' + (karate.properties['test.server.port'] || '3000');
		break;
	case 'preprod':
		config.baseUrl = 'http://preprod.demo.com';
		break;
  }
  return config;
}