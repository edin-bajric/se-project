import ReactPlayer from "react-player";

type Props = {
  video: string;
};

const MoviePlayer = ({ video }: Props) => {
  return <ReactPlayer url={video} controls width="100%" height="100%" />;
};

export default MoviePlayer;
