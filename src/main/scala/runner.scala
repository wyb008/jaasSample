import javax.security.auth._
import javax.security.auth.callback._
import javax.security.auth.login._
import com.sun.security.auth.callback.TextCallbackHandler
import javax.security.auth.callback._

object runner {
  def main(arg: Array[String]){
        
    //val  lc = new LoginContext("JaasSample",new TextCallbackHandler())
    val  lc = new LoginContext("JaasSample",new SampleCallbackHandler("au9uusr","Au9uacc1"))
    
    try{
      lc.login()
    }
    catch{
      case ex: LoginException => println("Authenticated Failed")
      System.exit(-1)
    }
    
    println("Authenticated Success!")
    
    val sb = lc.getSubject
    
    import scala.collection.JavaConverters._
    
    val prins = sb.getPrincipals().asScala
    
    for(p <- prins){
    
      println(s"You are login as: ${p.getName}")
    }
    
    lc.logout()
  }
}

class SampleCallbackHandler(userName: String, pwd: String) extends CallbackHandler {
  
  override def handle(callbacks: Array[Callback]) {
    
    for(callBack <- callbacks){
      println(s"processing the callBack ${callBack.toString()}")
      if(callBack.isInstanceOf[NameCallback]){
        val ncb = callBack.asInstanceOf[NameCallback]
        ncb.setName(this.userName)
      }
     
      if(callBack.isInstanceOf[PasswordCallback]){
        val pcb =callBack.asInstanceOf[PasswordCallback]
        pcb.setPassword(this.pwd.toCharArray())
      }
    }
    
    println("----------------------------------------------------------------")
  }
}