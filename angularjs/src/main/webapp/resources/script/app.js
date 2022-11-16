var app = angular.module('loja', ['ngRoute', 'ngResource']);

app.controller('primeiroController', ['$scope', function($scope){
	$scope.user = {meunome : 'William Fernandes'};
	
	$scope.contador = 0;
	
	$scope.addContador = function(){
		$scope.contador++;
	}
	
}]);

app.config(function($routeProvider, $provide, $httpProvider, $locationProvider){
	$routeProvider
	.when("/clientelist", {controller: "clienteController", templateUrl: "cliente/list.html"})
	.when("/cliente/:id", {controller: "clienteController", templateUrl: "cliente/form.html"})
	.when("/cliente/cadastro", {controller: "clienteController", templateUrl: "cliente/form.html"})
	.otherwise({redirectTo: "/"})
});

app.controller('controllerPessoa', function($scope, $resource){
	
	// Com springFramework restufull
	// pessoas = $resource("/pessoas/:codPessoa");
	
	// Com servlets
	pessoas = $resource("/angularjs/pessoas/?codPessoa=:codPessoa"); 
	
	$scope.getPorId = function(){
		pessoas.get({codPessoa: $scope.codPessoa}, function(data){
			$scope.objetoPessoa = data;
		});
	}
});

app.controller("filterController", function($scope){
	
	$scope.friends = [{
		
		nome: "Maria", sobrenome: "Fernanda", idade: 16
	}, {
		
	 	nome: "Juliana", sobrenome: "Fernanda", idade: 19
	}]
});
