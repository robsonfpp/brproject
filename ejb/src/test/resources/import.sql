insert into usuario(nome,login,senha,email,endereco,ddd,telefone,gp) values('Robson Cardoso','rcardoso','123456','robsonfpp@gmail.com','Rua Sao Wenceslau 11, atpo 111','11','82972231',1);
insert into usuario(nome,login,senha,email,endereco,ddd,telefone,gp) values('Fernando Reis','freis','123456','stakeholder1@gmail.com','Rua Sao Wenceslau 11, atpo 111','11','82972231',0);
insert into usuario(nome,login,senha,email,endereco,ddd,telefone,gp) values('Marcelo Rezende','mrezende','123456','stakeholder2@gmail.com','Rua Sao Wenceslau 11, atpo 111','11','82972231',0);
insert into usuario(nome,login,senha,email,endereco,ddd,telefone,gp) values('Edilson Barbosa','ebarbosa','123456','stakeholder3@gmail.com','Rua Sao Wenceslau 11, atpo 111','11','82972231',0);
insert into usuario(nome,login,senha,email,endereco,ddd,telefone,gp) values('Diva Ribeiro','dribeiro','123456','stakeholder4@gmail.com','Rua Sao Wenceslau 11, atpo 111','11','82972231',0);

insert into tiporecurso(nome) values('Humano');
insert into tiporecurso(nome) values('Material');

insert into tipocusto(nome) values('Fixo');
insert into tipocusto(nome) values('Por Hora');

insert into nota(nome,valor) values('Muito menos importante',0.1);
insert into nota(nome,valor) values('Menos importante',0.2);
insert into nota(nome,valor) values('Igualmente importante',1.0);
insert into nota(nome,valor) values('Mais importante',5.0);
insert into nota(nome,valor) values('Muito mais importante',10.0);

insert into prioridadelicao(nome,valor) values('Muito importante',1);
insert into prioridadelicao(nome,valor) values('Importante',2);
insert into prioridadelicao(nome,valor) values('Pouco importante',3);
insert into prioridadelicao(nome,valor) values('Não importante',4);

insert into impactolicao(nome) values('Negativa');
insert into impactolicao(nome) values('Positiva');

insert into areaimpactada(nome) values('Custo');
insert into areaimpactada(nome) values('RH');
insert into areaimpactada(nome) values('Comunicacao');
insert into areaimpactada(nome) values('Qualidade');
insert into areaimpactada(nome) values('Tempo');
insert into areaimpactada(nome) values('Aquisicoes');

insert into statusmudanca(nome) values('Aberto');
insert into statusmudanca(nome) values('Aprovado');
insert into statusmudanca(nome) values('Reprovado');

insert into statusprojeto(nome) values('Em iniciacao');
insert into statusprojeto(nome) values('Em planejamento');
insert into statusprojeto(nome) values('Em execucao');
insert into statusprojeto(nome) values('Em finalizacao');
insert into statusprojeto(nome) values('Encerrado');

insert into projeto(statusprojetoid,gerenteprojetoid,nome,cliente,resumo,justificativa,necessidades,produto,premissas,restricoes,responsabilidadesgp,datacriacao,inicio,fimprevisto,orcamento) values(1,1,'Projeto Novas Fronteiras','Macro Solutions','Lorem ipsum dolor sit amet, consectetur adipiscing elit. In at fringilla sem. Donec mi nisi, interdum consectetur pellentesque condimentum, tincidunt a lacus. Phasellus aliquam sollicitudin gravida. Curabitur lobortis libero et sapien pellentesque malesuada. Etiam lacus urna, malesuada at dignissim elementum, ornare nec ligula. Proin vulputate faucibus lobortis. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Sed pharetra bibendum volutpat. Morbi ac augue quam. Donec ac orci non massa aliquet tempus. Sed aliquam lobortis turpis, interdum porttitor leo cursus id. Fusce hendrerit, nisl id bibendum dictum, metus lorem blandit nisi, non tempus libero massa in nunc. Nam rhoncus adipiscing massa, sed rutrum augue ullamcorper eu. Aenean rhoncus lobortis erat, ut ornare urna blandit ac.','Vivamus id sagittis lorem. Praesent pellentesque, enim eu vulputate placerat, leo quam semper sem, a adipiscing erat massa vel eros. Sed a libero dui. Phasellus scelerisque accumsan urna, nec molestie justo lacinia sed. Cras fringilla justo vel est cursus fermentum. Sed bibendum pharetra risus, in volutpat leo tempor et. Duis vel nibh augue. Nullam in mi non justo consequat semper ut vitae mi. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Mauris facilisis tortor quis risus mattis pellentesque.','Fusce in lectus nec mi commodo ornare. Sed vestibulum porta placerat. Donec ultrices vestibulum velit quis volutpat. Fusce ut erat risus, vel porta nibh.','orem ipsum dolor sit amet, consectetur adipiscing elit. Sed ultrices quam sit amet est dapibus eget pretium leo ultrices.','Proin eget mauris egestas magna placerat placerat. Vestibulum nunc nunc, posuere quis convallis ut, euismod vitae libero. Mauris faucibus nisl tincidunt risus pharetra faucibus. Fusce faucibus tincidunt venenatis. Aliquam feugiat pharetra quam sed fermentum. Nulla facilisi.','sum, quis tincidunt ligula dignissim non. Ut sollicitudin scelerisque tincidun','Curabitur nec pharetra lorem. Vivamus vel augue tellus, sit amet tempus arcu. Nulla ultricies velit sit amet odio ultrices condimentum. Nulla facilisi.','2011-09-01','2011-10-01','2010-10-01',9000.0);

insert into stakeholder(projetoid,usuarioid,ccb,papel) values(1,1,1,'Gerente de Projetos');
insert into stakeholder(projetoid,usuarioid,ccb,papel) values(1,2,1,'Representante financeiro');
insert into stakeholder(projetoid,usuarioid,ccb,papel) values(1,3,1,'Patrocinador');
insert into stakeholder(projetoid,usuarioid,ccb,papel) values(1,4,0,'Representante vendas');
insert into stakeholder(projetoid,usuarioid,ccb,papel) values(1,5,1,'Diretor de marketing');

insert into notastakeholder(projetoid,notaid,stakeholderavaliadoid,stakeholderavaliadorid) values(1,1,1,2);
insert into notastakeholder(projetoid,notaid,stakeholderavaliadoid,stakeholderavaliadorid) values(1,3,1,3);
insert into notastakeholder(projetoid,notaid,stakeholderavaliadoid,stakeholderavaliadorid) values(1,1,1,4);
insert into notastakeholder(projetoid,notaid,stakeholderavaliadoid,stakeholderavaliadorid) values(1,4,1,5);
insert into notastakeholder(projetoid,notaid,stakeholderavaliadoid,stakeholderavaliadorid) values(1,1,2,3);
insert into notastakeholder(projetoid,notaid,stakeholderavaliadoid,stakeholderavaliadorid) values(1,2,2,4);
insert into notastakeholder(projetoid,notaid,stakeholderavaliadoid,stakeholderavaliadorid) values(1,5,2,5);
insert into notastakeholder(projetoid,notaid,stakeholderavaliadoid,stakeholderavaliadorid) values(1,4,3,4);
insert into notastakeholder(projetoid,notaid,stakeholderavaliadoid,stakeholderavaliadorid) values(1,3,3,5);
insert into notastakeholder(projetoid,notaid,stakeholderavaliadoid,stakeholderavaliadorid) values(1,2,4,5);
insert into notastakeholder(projetoid,notaid,stakeholderavaliadoid,stakeholderavaliadorid) values(1,1,2,1);
insert into notastakeholder(projetoid,notaid,stakeholderavaliadoid,stakeholderavaliadorid) values(1,1,3,1);
insert into notastakeholder(projetoid,notaid,stakeholderavaliadoid,stakeholderavaliadorid) values(1,4,4,1);
insert into notastakeholder(projetoid,notaid,stakeholderavaliadoid,stakeholderavaliadorid) values(1,2,5,1);
insert into notastakeholder(projetoid,notaid,stakeholderavaliadoid,stakeholderavaliadorid) values(1,1,3,2);
insert into notastakeholder(projetoid,notaid,stakeholderavaliadoid,stakeholderavaliadorid) values(1,5,4,2);
insert into notastakeholder(projetoid,notaid,stakeholderavaliadoid,stakeholderavaliadorid) values(1,1,5,2);
insert into notastakeholder(projetoid,notaid,stakeholderavaliadoid,stakeholderavaliadorid) values(1,3,4,3);
insert into notastakeholder(projetoid,notaid,stakeholderavaliadoid,stakeholderavaliadorid) values(1,2,5,3);
insert into notastakeholder(projetoid,notaid,stakeholderavaliadoid,stakeholderavaliadorid) values(1,2,5,4);

insert into tarefa(projetoid,nome,inicio,fim,porcentcomp,milestone) values(1,'Dignostico','2011-01-01','2011-01-15',0.0,0);
insert into tarefa(projetoid,nome,inicio,fim,porcentcomp,milestone) values(1,'Dignostico1','2011-01-01','2011-01-05',0.0,0);
insert into tarefa(projetoid,nome,inicio,fim,porcentcomp,milestone) values(1,'Dignostico2','2011-01-06','2011-01-15',0.0,0);
insert into tarefa(projetoid,nome,inicio,fim,porcentcomp,milestone) values(1,'Dignostico3','2011-01-06','2011-01-10',0.0,0);
insert into tarefa(projetoid,nome,inicio,fim,porcentcomp,milestone) values(1,'Dignostico4','2011-01-11','2011-01-15',0.0,0);
insert into tarefa(projetoid,nome,inicio,fim,porcentcomp,milestone) values(1,'Software','2011-01-15','2011-03-30',0.0,0);
insert into tarefa(projetoid,nome,inicio,fim,porcentcomp,milestone) values(1,'Software1','2011-01-15','2011-01-30',0.0,0);
insert into tarefa(projetoid,nome,inicio,fim,porcentcomp,milestone) values(1,'Software2','2011-01-15','2011-02-15',0.0,0);
insert into tarefa(projetoid,nome,inicio,fim,porcentcomp,milestone) values(1,'Software3','2011-02-16','2011-02-28',0.0,0);
insert into tarefa(projetoid,nome,inicio,fim,porcentcomp,milestone) values(1,'Software4','2011-03-01','2011-03-20',0.0,0);
insert into tarefa(projetoid,nome,inicio,fim,porcentcomp,milestone) values(1,'Hardware','2011-01-15','2011-02-28',0.0,0);
insert into tarefa(projetoid,nome,inicio,fim,porcentcomp,milestone) values(1,'Hardware1','2011-01-15','2011-02-05',0.0,0);
insert into tarefa(projetoid,nome,inicio,fim,porcentcomp,milestone) values(1,'Hardware2','2011-02-06','2011-02-20',0.0,0);
insert into tarefa(projetoid,nome,inicio,fim,porcentcomp,milestone) values(1,'Hardware3','2011-03-21','2011-02-28',0.0,0);
insert into tarefa(projetoid,nome,inicio,fim,porcentcomp,milestone) values(1,'Treinamento','2011-01-16','2011-04-30',0.0,0);
insert into tarefa(projetoid,nome,inicio,fim,porcentcomp,milestone) values(1,'Treinamento1','2011-01-16','2011-01-20',0.0,0);
insert into tarefa(projetoid,nome,inicio,fim,porcentcomp,milestone) values(1,'Treinamento2','2011-01-21','2011-02-28',0.0,0);
insert into tarefa(projetoid,nome,inicio,fim,porcentcomp,milestone) values(1,'Treinamento3','2011-03-01','2011-03-30',0.0,0);
insert into tarefa(projetoid,nome,inicio,fim,porcentcomp,milestone) values(1,'Padronizacao','2011-05-01','2011-05-20',0.0,0);
insert into tarefa(projetoid,nome,inicio,fim,porcentcomp,milestone) values(1,'Padronizacao1','2011-05-01','2011-05-10',0.0,0);
insert into tarefa(projetoid,nome,inicio,fim,porcentcomp,milestone) values(1,'Padronizacao2','2011-05-11','2011-05-20',0.0,0);
insert into tarefa(projetoid,nome,inicio,fim,porcentcomp,milestone) values(1,'Marco 1','2011-05-21','2011-05-21',0.0,1);
insert into tarefa(projetoid,nome,inicio,fim,porcentcomp,milestone) values(1,'Piloto','2011-05-23','2011-06-30',0.0,0);
insert into tarefa(projetoid,nome,inicio,fim,porcentcomp,milestone) values(1,'Piloto1','2011-05-23','2011-05-30',0.0,0);
insert into tarefa(projetoid,nome,inicio,fim,porcentcomp,milestone) values(1,'Piloto2','2011-06-01','2011-06-15',0.0,0);
insert into tarefa(projetoid,nome,inicio,fim,porcentcomp,milestone) values(1,'Piloto3','2011-06-01','2011-06-30',0.0,0);
insert into tarefa(projetoid,nome,inicio,fim,porcentcomp,milestone) values(1,'Piloto31','2011-06-01','2011-06-30',0.0,0);
insert into tarefa(projetoid,nome,inicio,fim,porcentcomp,milestone) values(1,'Piloto32','2011-06-01','2011-06-10',0.0,0);
insert into tarefa(projetoid,nome,inicio,fim,porcentcomp,milestone) values(1,'Piloto33','2011-06-11','2011-06-20',0.0,0);
insert into tarefa(projetoid,nome,inicio,fim,porcentcomp,milestone) values(1,'Piloto34','2011-06-21','2011-06-30',0.0,0);
insert into tarefa(projetoid,nome,inicio,fim,porcentcomp,milestone) values(1,'Resultados','2011-07-01','2011-07-05',0.0,0);
insert into tarefa(projetoid,nome,inicio,fim,porcentcomp,milestone) values(1,'Resultados1','2011-07-01','2011-07-03',0.0,0);
insert into tarefa(projetoid,nome,inicio,fim,porcentcomp,milestone) values(1,'Resultados2','2011-07-01','2011-07-05',0.0,0);

insert into tarefapredecessora(tarefapredecessoraid,tarefaid) values(1,6);
insert into tarefapredecessora(tarefapredecessoraid,tarefaid) values(1,11);
insert into tarefapredecessora(tarefapredecessoraid,tarefaid) values(1,15);
insert into tarefapredecessora(tarefapredecessoraid,tarefaid) values(15,19);
insert into tarefapredecessora(tarefapredecessoraid,tarefaid) values(19,22);
insert into tarefapredecessora(tarefapredecessoraid,tarefaid) values(22,23);
insert into tarefapredecessora(tarefapredecessoraid,tarefaid) values(23,31);

insert into subtarefa(tarefaid,subtarefaid) values(1,2);
insert into subtarefa(tarefaid,subtarefaid) values(1,3);
insert into subtarefa(tarefaid,subtarefaid) values(1,4);
insert into subtarefa(tarefaid,subtarefaid) values(1,5);
insert into subtarefa(tarefaid,subtarefaid) values(6,7);
insert into subtarefa(tarefaid,subtarefaid) values(6,8);
insert into subtarefa(tarefaid,subtarefaid) values(6,9);
insert into subtarefa(tarefaid,subtarefaid) values(6,10);
insert into subtarefa(tarefaid,subtarefaid) values(11,12);
insert into subtarefa(tarefaid,subtarefaid) values(11,13);
insert into subtarefa(tarefaid,subtarefaid) values(11,14);
insert into subtarefa(tarefaid,subtarefaid) values(15,16);
insert into subtarefa(tarefaid,subtarefaid) values(15,17);
insert into subtarefa(tarefaid,subtarefaid) values(15,18);
insert into subtarefa(tarefaid,subtarefaid) values(19,20);
insert into subtarefa(tarefaid,subtarefaid) values(19,21);
insert into subtarefa(tarefaid,subtarefaid) values(23,24);
insert into subtarefa(tarefaid,subtarefaid) values(23,25);
insert into subtarefa(tarefaid,subtarefaid) values(23,26);
insert into subtarefa(tarefaid,subtarefaid) values(26,27);
insert into subtarefa(tarefaid,subtarefaid) values(26,28);
insert into subtarefa(tarefaid,subtarefaid) values(26,29);
insert into subtarefa(tarefaid,subtarefaid) values(26,30);
insert into subtarefa(tarefaid,subtarefaid) values(31,32);
insert into subtarefa(tarefaid,subtarefaid) values(31,33);

insert into recurso(nome,tiporecursoid) values('Xine',1);
insert into recurso(nome,tiporecursoid) values('Leston',1);
insert into recurso(nome,tiporecursoid) values('Marcelo',1);
insert into recurso(nome,tiporecursoid) values('Plinio',1);
insert into recurso(nome,tiporecursoid) values('Diva',1);
insert into recurso(nome,tiporecursoid) values('Edilson',1);
insert into recurso(nome,tiporecursoid) values('Dario',1);
insert into recurso(nome,tiporecursoid) values('Silvia',1);
insert into recurso(nome,tiporecursoid) values('Fernando',1);
insert into recurso(nome,tiporecursoid) values('Nilvia',1);
insert into recurso(nome,tiporecursoid) values('Paulo',1);
insert into recurso(nome,tiporecursoid) values('Sergio',1);
insert into recurso(nome,tiporecursoid) values('Pablo',1);
insert into recurso(nome,tiporecursoid) values('Renato',1);
insert into recurso(nome,tiporecursoid) values('Jaqueline',1);
insert into recurso(nome,tiporecursoid) values('Delbianco',1);

insert into utilizacaorecurso(recursoid,tarefaid,custo,tipocustoid) values(1,2,40000.0,1);
insert into utilizacaorecurso(recursoid,tarefaid,custo,tipocustoid) values(2,3,15.0,2);
insert into utilizacaorecurso(recursoid,tarefaid,custo,tipocustoid) values(3,4,15.0,2);
insert into utilizacaorecurso(recursoid,tarefaid,custo,tipocustoid) values(4,5,15.0,2);
insert into utilizacaorecurso(recursoid,tarefaid,custo,tipocustoid) values(5,7,15.0,2);
insert into utilizacaorecurso(recursoid,tarefaid,custo,tipocustoid) values(6,8,15.0,2);
insert into utilizacaorecurso(recursoid,tarefaid,custo,tipocustoid) values(7,9,15.0,2);
insert into utilizacaorecurso(recursoid,tarefaid,custo,tipocustoid) values(8,10,15.0,2);
insert into utilizacaorecurso(recursoid,tarefaid,custo,tipocustoid) values(9,12,15.0,2);
insert into utilizacaorecurso(recursoid,tarefaid,custo,tipocustoid) values(10,13,15.0,2);
insert into utilizacaorecurso(recursoid,tarefaid,custo,tipocustoid) values(11,14,15.0,2);
insert into utilizacaorecurso(recursoid,tarefaid,custo,tipocustoid) values(12,16,15.0,2);
insert into utilizacaorecurso(recursoid,tarefaid,custo,tipocustoid) values(13,17,15.0,2);
insert into utilizacaorecurso(recursoid,tarefaid,custo,tipocustoid) values(14,18,15.0,2);
insert into utilizacaorecurso(recursoid,tarefaid,custo,tipocustoid) values(15,20,15.0,2);
insert into utilizacaorecurso(recursoid,tarefaid,custo,tipocustoid) values(16,21,15.0,2);
insert into utilizacaorecurso(recursoid,tarefaid,custo,tipocustoid) values(1,24,15.0,2);
insert into utilizacaorecurso(recursoid,tarefaid,custo,tipocustoid) values(2,25,15.0,2);
insert into utilizacaorecurso(recursoid,tarefaid,custo,tipocustoid) values(3,27,15.0,2);
insert into utilizacaorecurso(recursoid,tarefaid,custo,tipocustoid) values(4,28,15.0,2);
insert into utilizacaorecurso(recursoid,tarefaid,custo,tipocustoid) values(5,29,15.0,2);
insert into utilizacaorecurso(recursoid,tarefaid,custo,tipocustoid) values(7,30,15.0,2);
insert into utilizacaorecurso(recursoid,tarefaid,custo,tipocustoid) values(8,31,15.0,2);
insert into utilizacaorecurso(recursoid,tarefaid,custo,tipocustoid) values(9,32,15.0,2);

insert into solicitacaomudanca(projetoid,statusmudancaid,usuarioid,dataabertura,descricao,titulo) values(1,1,5,'2011-02-28','Mudar bla bla bla bla lba Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum','Alteracao de Lorem ipsun dollor etc');
insert into solicitacaomudanca(projetoid,statusmudancaid,usuarioid,dataabertura,datafechamento,descricao,titulo,justificativa) values(1,2,3,'2011-03-30','2011-04-05','Mudar bla bla bla bla lba Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum','Alteracao de Outra coisa ...','Alteraćao aceita, irá impactar no sucesso do projeto e bla bla bl alba lba lbal blablblablbablaalbmamsdk emcnocmam, rm vkm    dsls  fkdkdkdksms cnchhddosaopallsmfkmg ajjf djncjab');

insert into licao(projetoid,areaimpactadaid,impactolicaoid,prioridadelicaoid,titulo,descricao) values(1,2,1,1,'Seguro saude do funcionário','Uma pessoa ficou doente e não tinha seguro bla bla bla bla bla ba ldslkdlmdskdsidndnsdbrnslclssmchf fjiajijpofe fiejfk;m sed do eiusmod tempor incididunt ut labore e');
insert into licao(projetoid,areaimpactadaid,impactolicaoid,prioridadelicaoid,titulo,descricao) values(1,5,2,4,'Atraso nas tarefas','At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio');