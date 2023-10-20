package fr.ovrckdlike.ppp.internal;

import static org.lwjgl.openal.AL10.AL_POSITION;
import static org.lwjgl.openal.AL10.AL_STOPPED;
import static org.lwjgl.openal.AL10.alBufferData;
import static org.lwjgl.openal.AL10.alDeleteBuffers;
import static org.lwjgl.openal.AL10.alDeleteSources;
import static org.lwjgl.openal.AL10.alGenBuffers;
import static org.lwjgl.openal.AL10.alGetSourcei;
import static org.lwjgl.openal.AL10.alSourcePlay;
import static org.lwjgl.openal.AL10.alSourceStop;
import static org.lwjgl.openal.AL10.alSourcef;
import static org.lwjgl.openal.AL10.alSourcei;
import static org.lwjgl.system.MemoryStack.stackMallocInt;
import static org.lwjgl.system.MemoryStack.stackPop;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.libc.LibCStdlib.free;

import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.Objects;
import org.lwjgl.openal.AL10;
import org.lwjgl.stb.STBVorbis;

/**
 * Une classe qui permet de jouer des sons avec OpenAL.
 */
public class Sound {
  private int bufferId;
  private int sourceId;
  private String filepath = "res/sounds/null.ogg";

  private final String name;

  private boolean isPlaying = false;
  private final boolean loops;

  /**
   * Constructeur de la classe Sound.
   *
   * @param name Le nom du son
   * @param filepath Le chemin vers le fichier audio
   * @param loops Si le son doit être joué en boucle
   */
  public Sound(String name, String filepath, boolean loops) {
    this.name = name;
    if (!Objects.equals(filepath, "")) {
      this.filepath = filepath;
    }
    this.loops = loops;

    loadSound();

  }

  private void loadSound() {
    // Allocation d'espace pour stocker le retour de STB
    try {
      stackPush();
      IntBuffer channelsBuffer = stackMallocInt(1);
      stackPush();
      IntBuffer sampleRateBuffer = stackMallocInt(1);

      ShortBuffer rawAudioBuffer = STBVorbis.stb_vorbis_decode_filename(
          filepath, channelsBuffer, sampleRateBuffer);
      if (rawAudioBuffer == null) {
        stackPop();
        stackPop();
        throw new RuntimeException("Failed to load audio file " + filepath + "!");
      }

      // Récupération des informations du buffer
      int channels = channelsBuffer.get();

      stackPop();

      // Trouver le bon format pour OpenAL
      int format = switch (channels) {
        case 1 -> AL10.AL_FORMAT_MONO16;
        case 2 -> AL10.AL_FORMAT_STEREO16;
        default -> throw new RuntimeException("Unsupported number of channels: " + channels);
      };

      int sampleRate = sampleRateBuffer.get();
      stackPop();

      bufferId = alGenBuffers();
      alBufferData(bufferId, format, rawAudioBuffer, sampleRate);

      // Generation de la source
      sourceId = AL10.alGenSources();

      alSourcei(sourceId, AL10.AL_BUFFER, bufferId);
      alSourcei(sourceId, AL10.AL_LOOPING, loops ? 1 : 0);
      alSourcei(sourceId, AL_POSITION, 0);
      alSourcef(sourceId, AL10.AL_GAIN, 0.3f);

      // Libération de la mémoire
      free(rawAudioBuffer);
    } catch (Exception e) {
      e.printStackTrace();
    }



  }

  public void delete() {
    alDeleteSources(sourceId);
    alDeleteBuffers(bufferId);
  }

  /**
   * Joue le son.
   */
  public void play() {
    int state = alGetSourcei(sourceId, AL10.AL_SOURCE_STATE);
    if (state == AL_STOPPED) {
      isPlaying = false;
      alSourcei(sourceId, AL_POSITION, 0);
    }

    if (!isPlaying) {
      alSourcePlay(sourceId);
      isPlaying = true;
    }
  }

  /**
   * Arrête le son.
   */
  public void stop() {
    if (isPlaying) {
      alSourceStop(sourceId);
      isPlaying = false;
    }
  }

  /**
   * Renvoie le chemin vers le fichier audio.
   *
   * @return Le chemin vers le fichier audio
   */
  public String getFilepath() {
    return filepath;
  }

  /**
   * Renvoie si le son est en train d'être joué.
   *
   * @return Si le son est en train d'être joué
   */
  public boolean isPlaying() {
    int state = alGetSourcei(sourceId, AL10.AL_SOURCE_STATE);
    if (state == AL_STOPPED) {
      isPlaying = false;
    }
    return isPlaying;
  }

  /**
   * Renvoie le nom du son.
   *
   * @return Le nom du son
   */
  public String getName() {
    return name;
  }


  /**
   * Change le chemin vers le fichier audio et charge le son.
   *
   * @param s Le nouveau chemin vers le fichier audio
   */
  public void setPath(String s) {
    filepath = s;
    this.loadSound();
  }
}
