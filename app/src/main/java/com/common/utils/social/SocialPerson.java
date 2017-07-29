package com.common.utils.social;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.code.linkedinapi.schema.Person;

import java.util.List;

public class SocialPerson implements Parcelable {

    public static final Creator<SocialPerson> CREATOR
            = new Creator<SocialPerson>() {
        public SocialPerson createFromParcel(Parcel in) {
            return new SocialPerson(in);
        }

        public SocialPerson[] newArray(int size) {
            return new SocialPerson[size];
        }
    };

    private String id;
    private String name;
    private String company;
    private String position;
    private String avatarURL;

    private String profileURL; // url to users profile, can be generated for twitter, facebook, but need to get via api from LinkedIn
    private String nickname;
    private String skills;

    public List<Person> personList;

    public SocialPerson() {

    }

    private SocialPerson(Parcel in) {
        id = in.readString();
        name = in.readString();
        company = in.readString();
        position = in.readString();
        avatarURL = in.readString();
        profileURL = in.readString();
        nickname = in.readString();
        skills = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(company);
        dest.writeString(position);
        dest.writeString(avatarURL);
        dest.writeString(profileURL);
        dest.writeString(nickname);
        dest.writeString(skills);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SocialPerson that = (SocialPerson) o;

        return avatarURL != null
                ? avatarURL.equals(that.avatarURL)
                : that.avatarURL == null && (company != null
                    ? company.equals(that.company)
                    : that.company == null && (id != null
                        ? id.equals(that.id)
                        : that.id == null && (name != null
                            ? name.equals(that.name)
                            : that.name == null && (nickname != null
                                ? nickname.equals(that.nickname)
                                : that.nickname == null && (position != null
                                    ? position.equals(that.position)
                                    : that.position == null && (profileURL != null
                                        ? profileURL.equals(that.profileURL)
                                        : that.profileURL == null && (skills != null
                                            ? skills.equals(that.skills)
                                            : that.skills == null)))))));
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (company != null ? company.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (avatarURL != null ? avatarURL.hashCode() : 0);
        result = 31 * result + (profileURL != null ? profileURL.hashCode() : 0);
        result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
        result = 31 * result + (skills != null ? skills.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SocialPerson{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", company='" + company + '\'' +
                ", position='" + position + '\'' +
                ", avatarURL='" + avatarURL + '\'' +
                ", profileURL='" + profileURL + '\'' +
                ", nickname='" + nickname + '\'' +
                ", skills='" + skills + '\'' +
                '}';
    }
}
