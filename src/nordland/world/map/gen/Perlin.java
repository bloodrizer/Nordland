
package nordland.world.map.gen;

import nordland.util.math.FastMath;

public class Perlin {

        private static float scalexsmall = 0.04f;
        private static float scaleysmall = 0.02f;
        private static float scalexbig = 0.015f;
        private static float scaleybig = 0.01f;
        private static float heightsmall = 3.0f;
        private static float heightbig = 10.0f;
        private static float speedsmall = 1.0f;
        private static float speedbig = 0.5f;
        private static int octaves = 1;



        public static float getHeight( float x, float z, float seed ) {

                float time = 1.0f;
                float zval = z * scaleybig * 4f + time * speedbig * 4f;
                float height = FastMath.sin( zval );
                height *= heightbig;
                if( octaves > 0 ) {
                        float height2 = (float) Noise.noise( x * scaleybig, z * scalexbig, time * speedbig ) * heightbig;
                        height = height * 0.4f + height2 * 0.6f;
                }
                if( octaves > 1 )
                        height += Noise.noise( x * scaleysmall, z * scalexsmall, time * speedsmall ) * heightsmall;
                if( octaves > 2 )
                        height += Noise.noise( x * scaleysmall * 2.0f, z * scalexsmall * 2.0f, time * speedsmall * 1.5f ) * heightsmall * 0.5f;
                if( octaves > 3 )
                        height += Noise.noise( x * scaleysmall * 4.0f, z * scalexsmall * 4.0f, time * speedsmall * 2.0f ) * heightsmall * 0.25f;
                return height; // + waterHeight
               

        }

}