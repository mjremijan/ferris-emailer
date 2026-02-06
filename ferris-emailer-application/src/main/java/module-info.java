
module FerrisEmailer {
    exports org.ferris.emailer.main;
    exports org.ferris.emailer.logger;
    requires FerrisEmailerDomain;
    requires org.slf4j;
    requires java.naming;
    requires ch.qos.logback.classic;
    requires ch.qos.logback.core;
    requires jakarta.mail;
}
