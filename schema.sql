SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `brproject` DEFAULT CHARACTER SET latin1 COLLATE latin1_bin ;
USE `brproject` ;

-- -----------------------------------------------------
-- Table `brproject`.`areaimpactada`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `brproject`.`areaimpactada` ;

CREATE  TABLE IF NOT EXISTS `brproject`.`areaimpactada` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `nome` VARCHAR(255) CHARACTER SET 'latin1' COLLATE 'latin1_bin' NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_bin;


-- -----------------------------------------------------
-- Table `brproject`.`impactolicao`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `brproject`.`impactolicao` ;

CREATE  TABLE IF NOT EXISTS `brproject`.`impactolicao` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `nome` VARCHAR(255) CHARACTER SET 'latin1' COLLATE 'latin1_bin' NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_bin;


-- -----------------------------------------------------
-- Table `brproject`.`prioridadelicao`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `brproject`.`prioridadelicao` ;

CREATE  TABLE IF NOT EXISTS `brproject`.`prioridadelicao` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `nome` VARCHAR(255) CHARACTER SET 'latin1' COLLATE 'latin1_bin' NULL DEFAULT NULL ,
  `valor` INT(11) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_bin;


-- -----------------------------------------------------
-- Table `brproject`.`usuario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `brproject`.`usuario` ;

CREATE  TABLE IF NOT EXISTS `brproject`.`usuario` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `ddd` VARCHAR(2) CHARACTER SET 'latin1' COLLATE 'latin1_bin' NULL DEFAULT NULL ,
  `email` VARCHAR(255) CHARACTER SET 'latin1' COLLATE 'latin1_bin' NULL DEFAULT NULL ,
  `endereco` VARCHAR(255) CHARACTER SET 'latin1' COLLATE 'latin1_bin' NULL DEFAULT NULL ,
  `gp` BIT(1) NULL DEFAULT NULL ,
  `login` VARCHAR(45) CHARACTER SET 'latin1' COLLATE 'latin1_bin' NOT NULL ,
  `nome` VARCHAR(255) CHARACTER SET 'latin1' COLLATE 'latin1_bin' NULL DEFAULT NULL ,
  `senha` VARCHAR(12) CHARACTER SET 'latin1' COLLATE 'latin1_bin' NOT NULL ,
  `telefone` VARCHAR(8) CHARACTER SET 'latin1' COLLATE 'latin1_bin' NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `login` (`login` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_bin;


-- -----------------------------------------------------
-- Table `brproject`.`statusprojeto`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `brproject`.`statusprojeto` ;

CREATE  TABLE IF NOT EXISTS `brproject`.`statusprojeto` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `nome` VARCHAR(255) CHARACTER SET 'latin1' COLLATE 'latin1_bin' NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_bin;


-- -----------------------------------------------------
-- Table `brproject`.`projeto`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `brproject`.`projeto` ;

CREATE  TABLE IF NOT EXISTS `brproject`.`projeto` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `cliente` VARCHAR(255) CHARACTER SET 'latin1' COLLATE 'latin1_bin' NULL DEFAULT NULL ,
  `custoprevisto` DOUBLE NULL DEFAULT NULL ,
  `datacriacao` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  `fim` DATETIME NULL DEFAULT NULL ,
  `fimprevisto` DATETIME NULL DEFAULT NULL ,
  `inicio` DATETIME NOT NULL ,
  `justificativa` LONGTEXT CHARACTER SET 'latin1' COLLATE 'latin1_bin' NULL DEFAULT NULL ,
  `necessidades` LONGTEXT CHARACTER SET 'latin1' COLLATE 'latin1_bin' NULL DEFAULT NULL ,
  `nome` VARCHAR(255) CHARACTER SET 'latin1' COLLATE 'latin1_bin' NULL DEFAULT NULL ,
  `orcamento` DOUBLE NULL DEFAULT NULL ,
  `premissas` LONGTEXT CHARACTER SET 'latin1' COLLATE 'latin1_bin' NULL DEFAULT NULL ,
  `produto` LONGTEXT CHARACTER SET 'latin1' COLLATE 'latin1_bin' NULL DEFAULT NULL ,
  `responsabilidadesgp` LONGTEXT CHARACTER SET 'latin1' COLLATE 'latin1_bin' NULL DEFAULT NULL ,
  `restricoes` LONGTEXT CHARACTER SET 'latin1' COLLATE 'latin1_bin' NULL DEFAULT NULL ,
  `resumo` LONGTEXT CHARACTER SET 'latin1' COLLATE 'latin1_bin' NULL DEFAULT NULL ,
  `statusprojetoid` BIGINT(20) NOT NULL ,
  `gerenteprojetoid` BIGINT(20) NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `FKED904D23CE9614EB` (`statusprojetoid` ASC) ,
  INDEX `FKED904D238412F2EC` (`gerenteprojetoid` ASC) ,
  CONSTRAINT `FKED904D238412F2EC`
    FOREIGN KEY (`gerenteprojetoid` )
    REFERENCES `brproject`.`usuario` (`id` ),
  CONSTRAINT `FKED904D23CE9614EB`
    FOREIGN KEY (`statusprojetoid` )
    REFERENCES `brproject`.`statusprojeto` (`id` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_bin;


-- -----------------------------------------------------
-- Table `brproject`.`licoes`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `brproject`.`licoes` ;

CREATE  TABLE IF NOT EXISTS `brproject`.`licoes` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `descricao` LONGTEXT CHARACTER SET 'latin1' COLLATE 'latin1_bin' NOT NULL ,
  `titulo` VARCHAR(255) CHARACTER SET 'latin1' COLLATE 'latin1_bin' NOT NULL ,
  `areaimpactadaid` BIGINT(20) NOT NULL ,
  `impactolicaoid` BIGINT(20) NOT NULL ,
  `prioridadelicaoid` BIGINT(20) NOT NULL ,
  `projetoid` BIGINT(20) NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `FKBE41B517BA4D13DF` (`areaimpactadaid` ASC) ,
  INDEX `FKBE41B517253A9843` (`impactolicaoid` ASC) ,
  INDEX `FKBE41B5176EE8134F` (`projetoid` ASC) ,
  INDEX `FKBE41B517E9C77A77` (`prioridadelicaoid` ASC) ,
  CONSTRAINT `FKBE41B517E9C77A77`
    FOREIGN KEY (`prioridadelicaoid` )
    REFERENCES `brproject`.`prioridadelicao` (`id` ),
  CONSTRAINT `FKBE41B517253A9843`
    FOREIGN KEY (`impactolicaoid` )
    REFERENCES `brproject`.`impactolicao` (`id` ),
  CONSTRAINT `FKBE41B5176EE8134F`
    FOREIGN KEY (`projetoid` )
    REFERENCES `brproject`.`projeto` (`id` ),
  CONSTRAINT `FKBE41B517BA4D13DF`
    FOREIGN KEY (`areaimpactadaid` )
    REFERENCES `brproject`.`areaimpactada` (`id` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_bin;


-- -----------------------------------------------------
-- Table `brproject`.`stakeholder`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `brproject`.`stakeholder` ;

CREATE  TABLE IF NOT EXISTS `brproject`.`stakeholder` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `ccb` BIT(1) NULL DEFAULT NULL ,
  `papel` VARCHAR(255) CHARACTER SET 'latin1' COLLATE 'latin1_bin' NULL DEFAULT NULL ,
  `projetoid` BIGINT(20) NOT NULL ,
  `usuarioid` BIGINT(20) NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `usuarioid` (`usuarioid` ASC, `projetoid` ASC) ,
  INDEX `FKC3AB8DA6F56008A5` (`usuarioid` ASC) ,
  INDEX `FKC3AB8DA66EE8134F` (`projetoid` ASC) ,
  CONSTRAINT `FKC3AB8DA66EE8134F`
    FOREIGN KEY (`projetoid` )
    REFERENCES `brproject`.`projeto` (`id` ),
  CONSTRAINT `FKC3AB8DA6F56008A5`
    FOREIGN KEY (`usuarioid` )
    REFERENCES `brproject`.`usuario` (`id` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_bin;


-- -----------------------------------------------------
-- Table `brproject`.`prioridadestakeholder`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `brproject`.`prioridadestakeholder` ;

CREATE  TABLE IF NOT EXISTS `brproject`.`prioridadestakeholder` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `nome` VARCHAR(255) CHARACTER SET 'latin1' COLLATE 'latin1_bin' NULL DEFAULT NULL ,
  `valor` DOUBLE NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_bin;


-- -----------------------------------------------------
-- Table `brproject`.`matrizprioristakeholder`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `brproject`.`matrizprioristakeholder` ;

CREATE  TABLE IF NOT EXISTS `brproject`.`matrizprioristakeholder` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `prioridadestakeholderid` BIGINT(20) NOT NULL ,
  `stakeholderavaliadoid` BIGINT(20) NOT NULL ,
  `stakeholderavaliadorid` BIGINT(20) NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `FK1CE2BC04B2DD2FDB` (`prioridadestakeholderid` ASC) ,
  INDEX `FK1CE2BC0465D958` (`stakeholderavaliadorid` ASC) ,
  INDEX `FK1CE2BC042EC1D098` (`stakeholderavaliadoid` ASC) ,
  CONSTRAINT `FK1CE2BC042EC1D098`
    FOREIGN KEY (`stakeholderavaliadoid` )
    REFERENCES `brproject`.`stakeholder` (`id` ),
  CONSTRAINT `FK1CE2BC0465D958`
    FOREIGN KEY (`stakeholderavaliadorid` )
    REFERENCES `brproject`.`stakeholder` (`id` ),
  CONSTRAINT `FK1CE2BC04B2DD2FDB`
    FOREIGN KEY (`prioridadestakeholderid` )
    REFERENCES `brproject`.`prioridadestakeholder` (`id` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_bin;


-- -----------------------------------------------------
-- Table `brproject`.`tiporecurso`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `brproject`.`tiporecurso` ;

CREATE  TABLE IF NOT EXISTS `brproject`.`tiporecurso` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `nome` VARCHAR(255) CHARACTER SET 'latin1' COLLATE 'latin1_bin' NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_bin;


-- -----------------------------------------------------
-- Table `brproject`.`recurso`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `brproject`.`recurso` ;

CREATE  TABLE IF NOT EXISTS `brproject`.`recurso` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `nome` VARCHAR(255) CHARACTER SET 'latin1' COLLATE 'latin1_bin' NULL DEFAULT NULL ,
  `tiporecursoid` BIGINT(20) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `FK4089DA499461E6B3` (`tiporecursoid` ASC) ,
  CONSTRAINT `FK4089DA499461E6B3`
    FOREIGN KEY (`tiporecursoid` )
    REFERENCES `brproject`.`tiporecurso` (`id` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_bin;


-- -----------------------------------------------------
-- Table `brproject`.`statusmudanca`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `brproject`.`statusmudanca` ;

CREATE  TABLE IF NOT EXISTS `brproject`.`statusmudanca` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `nome` VARCHAR(255) CHARACTER SET 'latin1' COLLATE 'latin1_bin' NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_bin;


-- -----------------------------------------------------
-- Table `brproject`.`solicitacaomudanca`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `brproject`.`solicitacaomudanca` ;

CREATE  TABLE IF NOT EXISTS `brproject`.`solicitacaomudanca` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `dataabertura` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  `datafechamento` DATETIME NULL DEFAULT NULL ,
  `descricao` LONGTEXT CHARACTER SET 'latin1' COLLATE 'latin1_bin' NOT NULL ,
  `justificativa` LONGTEXT CHARACTER SET 'latin1' COLLATE 'latin1_bin' NULL DEFAULT NULL ,
  `titulo` VARCHAR(255) CHARACTER SET 'latin1' COLLATE 'latin1_bin' NOT NULL ,
  `projetoid` BIGINT(20) NOT NULL ,
  `statusmudancaid` BIGINT(20) NOT NULL ,
  `usuarioid` BIGINT(20) NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `FK63B5C0C2F56008A5` (`usuarioid` ASC) ,
  INDEX `FK63B5C0C26EE8134F` (`projetoid` ASC) ,
  INDEX `FK63B5C0C25A6FE7B3` (`statusmudancaid` ASC) ,
  CONSTRAINT `FK63B5C0C25A6FE7B3`
    FOREIGN KEY (`statusmudancaid` )
    REFERENCES `brproject`.`statusmudanca` (`id` ),
  CONSTRAINT `FK63B5C0C26EE8134F`
    FOREIGN KEY (`projetoid` )
    REFERENCES `brproject`.`projeto` (`id` ),
  CONSTRAINT `FK63B5C0C2F56008A5`
    FOREIGN KEY (`usuarioid` )
    REFERENCES `brproject`.`usuario` (`id` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_bin;


-- -----------------------------------------------------
-- Table `brproject`.`tarefa`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `brproject`.`tarefa` ;

CREATE  TABLE IF NOT EXISTS `brproject`.`tarefa` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `fim` DATETIME NOT NULL ,
  `inicio` DATETIME NOT NULL ,
  `milestone` BIT(1) NULL DEFAULT NULL ,
  `nome` VARCHAR(255) CHARACTER SET 'latin1' COLLATE 'latin1_bin' NULL DEFAULT NULL ,
  `porcentcomp` DOUBLE NULL DEFAULT NULL ,
  `projetoid` BIGINT(20) NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `FKCB7E6A1B6EE8134F` (`projetoid` ASC) ,
  CONSTRAINT `FKCB7E6A1B6EE8134F`
    FOREIGN KEY (`projetoid` )
    REFERENCES `brproject`.`projeto` (`id` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_bin;


-- -----------------------------------------------------
-- Table `brproject`.`subtarefa`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `brproject`.`subtarefa` ;

CREATE  TABLE IF NOT EXISTS `brproject`.`subtarefa` (
  `subtarefaid` BIGINT(20) NOT NULL ,
  `tarefaid` BIGINT(20) NOT NULL ,
  PRIMARY KEY (`subtarefaid`, `tarefaid`) ,
  INDEX `FK2048785B5C454E63` (`subtarefaid` ASC) ,
  INDEX `FK2048785B11C5D023` (`tarefaid` ASC) ,
  CONSTRAINT `FK2048785B11C5D023`
    FOREIGN KEY (`tarefaid` )
    REFERENCES `brproject`.`tarefa` (`id` ),
  CONSTRAINT `FK2048785B5C454E63`
    FOREIGN KEY (`subtarefaid` )
    REFERENCES `brproject`.`tarefa` (`id` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_bin;


-- -----------------------------------------------------
-- Table `brproject`.`tarefapredecessora`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `brproject`.`tarefapredecessora` ;

CREATE  TABLE IF NOT EXISTS `brproject`.`tarefapredecessora` (
  `tarefapredecessoraid` BIGINT(20) NOT NULL ,
  `tarefaid` BIGINT(20) NOT NULL ,
  PRIMARY KEY (`tarefapredecessoraid`, `tarefaid`) ,
  INDEX `FK209A413D26A6C1B` (`tarefapredecessoraid` ASC) ,
  INDEX `FK209A41311C5D023` (`tarefaid` ASC) ,
  CONSTRAINT `FK209A41311C5D023`
    FOREIGN KEY (`tarefaid` )
    REFERENCES `brproject`.`tarefa` (`id` ),
  CONSTRAINT `FK209A413D26A6C1B`
    FOREIGN KEY (`tarefapredecessoraid` )
    REFERENCES `brproject`.`tarefa` (`id` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_bin;


-- -----------------------------------------------------
-- Table `brproject`.`tipocusto`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `brproject`.`tipocusto` ;

CREATE  TABLE IF NOT EXISTS `brproject`.`tipocusto` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `nome` VARCHAR(255) CHARACTER SET 'latin1' COLLATE 'latin1_bin' NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_bin;


-- -----------------------------------------------------
-- Table `brproject`.`utilizacaorecurso`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `brproject`.`utilizacaorecurso` ;

CREATE  TABLE IF NOT EXISTS `brproject`.`utilizacaorecurso` (
  `recursoid` BIGINT(20) NOT NULL ,
  `tarefaid` BIGINT(20) NOT NULL ,
  `custo` DOUBLE NULL DEFAULT NULL ,
  `tipocustoid` BIGINT(20) NOT NULL ,
  PRIMARY KEY (`recursoid`, `tarefaid`) ,
  INDEX `FK12D9F5E62D7893D9` (`tipocustoid` ASC) ,
  INDEX `FK12D9F5E63CAC7C1B` (`recursoid` ASC) ,
  INDEX `FK12D9F5E611C5D023` (`tarefaid` ASC) ,
  CONSTRAINT `FK12D9F5E611C5D023`
    FOREIGN KEY (`tarefaid` )
    REFERENCES `brproject`.`tarefa` (`id` ),
  CONSTRAINT `FK12D9F5E62D7893D9`
    FOREIGN KEY (`tipocustoid` )
    REFERENCES `brproject`.`tipocusto` (`id` ),
  CONSTRAINT `FK12D9F5E63CAC7C1B`
    FOREIGN KEY (`recursoid` )
    REFERENCES `brproject`.`recurso` (`id` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_bin;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;