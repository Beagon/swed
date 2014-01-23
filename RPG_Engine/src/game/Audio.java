/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
/**
 *
 * @author Robin
 */
public class Audio {

        
    public Audio() {
        
    }

    public static void PlayMidi(String File){
       	Clip clip;
		try {
			AudioInputStream soundStream = AudioSystem.getAudioInputStream(Game.class.getResource("/" + File + ".mid"));
			AudioFormat audioFormat = soundStream.getFormat();
			// define line information based on line type,
			// encoding and frame sizes of audio file
			DataLine.Info dataLineInfo = new DataLine.Info(
					Clip.class, AudioSystem.getTargetFormats(
							AudioFormat.Encoding.PCM_SIGNED, audioFormat ),
							audioFormat.getFrameSize(),
							audioFormat.getFrameSize() * 2 );
	
			// make sure sound system supports data line
			if ( !AudioSystem.isLineSupported( dataLineInfo ) ) {
				System.err.println( "Unsupported Clip File!" );
				return;
			}
			// get clip line resource
			clip = ( Clip ) AudioSystem.getLine( dataLineInfo );
			// open audio clip and get required system resources
			clip.open( soundStream );
                        clip.start();
		}
		catch ( Exception e) {
			e.printStackTrace();
		}

	}
    
}
