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
import org.lwjgl.openal.AL10;
import org.lwjgl.stb.STBVorbis;

public class Sound {
  private int bufferId;
  private int sourceId;
  private String filepath;

  private String name;

  private boolean isPlaying = false;

  public Sound(String name, String filepath, boolean loops) {
    this.name = name;
    this.filepath = filepath;

    // Allocation d'espace pour stocker le retour de STB
    stackPush();
    IntBuffer channelsBuffer = stackMallocInt(1);
    stackPush();
    IntBuffer sampleRateBuffer = stackMallocInt(1);

    ShortBuffer rawAudioBuffer =
        STBVorbis.stb_vorbis_decode_filename(filepath, channelsBuffer, sampleRateBuffer);
    if (rawAudioBuffer == null) {
      stackPop();
      stackPop();
      throw new RuntimeException("Failed to load audio file " + filepath + "!");
    }

    // Récupération des informations du buffer
    int channels = channelsBuffer.get();
    int sampleRate = sampleRateBuffer.get();

    stackPop();
    stackPop();

    // Trouver le bon format pour OpenAL
    int format = -1;
    switch (channels) {
      case 1:
        format = AL10.AL_FORMAT_MONO16;
        break;
      case 2:
        format = AL10.AL_FORMAT_STEREO16;
        break;
      default:
        throw new RuntimeException("Unsupported number of channels: " + channels);
    }

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
  }

  public void delete() {
    alDeleteSources(sourceId);
    alDeleteBuffers(bufferId);
  }

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

  public void stop() {
    if (isPlaying) {
      alSourceStop(sourceId);
      isPlaying = false;
    }
  }

  public String getFilepath() {
    return filepath;
  }

  public boolean isPlaying() {
    int state = alGetSourcei(sourceId, AL10.AL_SOURCE_STATE);
    if (state == AL_STOPPED) {
      isPlaying = false;
    }
    return isPlaying;
  }

  public String getName() {
    return name;
  }


}
